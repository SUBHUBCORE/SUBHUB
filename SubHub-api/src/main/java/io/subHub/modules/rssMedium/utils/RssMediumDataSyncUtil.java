package io.subHub.modules.rssMedium.utils;

import com.rometools.modules.content.ContentModule;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.subHub.common.utils.Result;
import io.subHub.modules.rssMedium.dto.RssMediumDTO;
import io.subHub.modules.rssMedium.service.RssMediumService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RssMediumDataSyncUtil {

    private static Logger logger = LoggerFactory.getLogger(RssMediumDataSyncUtil.class);

    public static Result dataSync(Long projectId,String mediumName,RssMediumService rssMediumService) {
        try {
            URL url = new URL("https://medium.com/feed/@"+mediumName);
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(url));
            List<SyndEntry> list = feed.getEntries();
            for (SyndEntry syndEntry : list){
                RssMediumDTO rssMediumDTO = rssMediumService.getByProjectIdAndGuid(projectId,syndEntry.getUri());
                if (rssMediumDTO == null){
                    rssMediumDTO = new RssMediumDTO();
                    rssMediumDTO.setProjectId(projectId);
                    rssMediumDTO.setTitle(syndEntry.getTitle());
                    ContentModule contentModule = (ContentModule) syndEntry.getModule(ContentModule.URI);
                    if (contentModule != null) {
                        List<String> encodes = contentModule.getEncodeds();
                        if (encodes != null && !encodes.isEmpty()) {
                            String html = encodes.get(0);
                            Document doc = Jsoup.parse(html);
                            Element firstH3 = doc.selectFirst("h3");
                            if (firstH3 != null) {
                                String h3Text = firstH3.text().trim();
                                if (h3Text.equals(syndEntry.getTitle().trim())) {
                                    firstH3.remove();
                                }
                            }
                            String finalContentHtml = doc.body().html();
                            rssMediumDTO.setContent(finalContentHtml);
                            String text = getTextPreview(finalContentHtml);
                            rssMediumDTO.setContentPart(text);
                        }
                    }else {
                        String html = syndEntry.getDescription().getValue();
                        rssMediumDTO.setContent(html);
                        String text = getTextPreview(html);
                        rssMediumDTO.setContentPart(text);
                    }
                    rssMediumDTO.setLink(syndEntry.getLink());
                    rssMediumDTO.setGuid(syndEntry.getUri());
                    rssMediumDTO.setContentPubDate(syndEntry.getPublishedDate());
                    rssMediumDTO.setCreateDt(new Date());
                    rssMediumDTO.setUpdateDt(new Date());
                    rssMediumService.save(rssMediumDTO);
                }
            }
            Long syncTotal = rssMediumService.getCountByProject(projectId);
            Map<String,Long> map = new HashMap<>();
            map.put("syncTotal",0L);
            if (syncTotal!=null){
                map.put("syncTotal",syncTotal);
            }
            return new Result().ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().error(e.getMessage());
        }
    }

    private static String getTextPreview(String html){
        int maxLength = 200;
        html = html.replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", "").trim();
        Document doc = Jsoup.parse(html);
        String text = doc.text();
        if (StringUtils.isNotEmpty(text)){
            if (text.length() > maxLength) {
                text = text.substring(0, maxLength) + "...";
            }
        }else {
            Element img = doc.selectFirst("img");
            if (img != null) {
                text =  img.attr("src");
            }else if (doc.selectFirst("video > source[src]")!=null){
                text = doc.selectFirst("video > source[src]").attr("src");
            }else {
                Element videoLink = doc.selectFirst("a[href$=.mp4], a[href*=youtube]");
                if (videoLink != null) {
                    text = videoLink.attr("href");
                }
            }
        }
        return text;
    }
}
