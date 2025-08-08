package io.subHub.modules.rssTwitter.utils;

import io.subHub.common.configBean.ConfigBean;
import io.subHub.modules.rssTwitter.dto.TwitterResponse;
import io.subHub.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class RssTwitterDataSyncUtil {
    private static Logger logger = LoggerFactory.getLogger(RssTwitterDataSyncUtil.class);

    public static TwitterResponse dataSync(String twitterId, int maxResults, ConfigBean configBean) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime startOfDay = now.toLocalDate().atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String startTime = formatter.format(startOfDay);
        String endTime = formatter.format(endOfDay);


        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.twitter.com/2/users/"+twitterId+"/tweets?" +
                "start_time="+startTime+"&end_time="+endTime+"&max_results="+maxResults+"" +
                "&exclude=replies,retweets&expansions=attachments.media_keys&media.fields=media_key,type,url,preview_image_url,variants&tweet.fields=created_at"; //
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer "+configBean.getBearer_token());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TwitterResponse> response = restTemplate.exchange(url,HttpMethod.GET,entity, TwitterResponse.class);
        return response.getBody();
    }
}
