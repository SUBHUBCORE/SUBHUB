package io.subHub.modules.rssTwitter.controller;


import io.subHub.common.configBean.ConfigBean;
import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.modules.security.user.SecurityUser;
import io.subHub.modules.security.user.UserDetail;
import io.subHub.common.constant.CommonConstant;
import io.subHub.common.constant.Constant;
import io.subHub.common.page.PageData;
import io.subHub.common.redis.RedisUtils;
import io.subHub.common.utils.Result;
import io.subHub.modules.rssTwitter.dto.*;
import io.subHub.modules.rssTwitter.service.RssTwitterService;
import io.subHub.modules.rssTwitter.utils.RssTwitterDataSyncUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;


@RestController
@RequestMapping("rss/twitter")
@Api(tags="RssTwitter")
public class RssTwitterController {
    private Logger logger = LoggerFactory.getLogger(RssTwitterController.class);


    @Resource
    private ProjectService projectsService;
    @Resource
    private RssTwitterService rssTwitterService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ConfigBean configBean;


    @GetMapping("page")
    @ApiOperation("PageList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "Current page number, starting from 1", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "Number of records displayed per page", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "sort field", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "Sorting method, optional values (asc, desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<RssTwitterDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }

        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        params.put("projectId",project.getId());
        if (StringUtils.isEmpty((String)params.get(Constant.ORDER_FIELD))){
            params.put(Constant.ORDER_FIELD,"content_pub_date");
        }
        if (StringUtils.isEmpty((String)params.get(Constant.ORDER))){
            params.put(Constant.ORDER,"desc");
        }
        PageData<RssTwitterDTO> page = rssTwitterService.page(params);
        return new Result<PageData<RssTwitterDTO>>().ok(page);
    }

    @GetMapping("sync")
    @ApiOperation("sync")
    public Result sync(){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        if (project.getCertifications() != 1 && project.getCertifications() != 5){
            return new Result().error(401,"Review failed");
        }
        if (StringUtils.isEmpty(project.getTwitterId())){
            return new Result().error(505);
        }
        Object rssTwitterCount = redisUtils.hGet(CommonConstant.RSS_TWITTER_COUNT,project.getId()+"");
        int rssTwitterCountInt = rssTwitterCount != null ? Integer.parseInt(rssTwitterCount.toString()) : 0;
        if (rssTwitterCountInt >= 30){
            return new Result().error(507,"You’ve reached this month’s sync limit (30).");
        }
        int maxResults = 30 - rssTwitterCountInt;
        if (maxResults<=5){
            maxResults = 5;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("syncTotal",0);
        map.put("surplusUsage",(30-rssTwitterCountInt));

        TwitterResponse twitterResponse = RssTwitterDataSyncUtil.dataSync(project.getTwitterId(),maxResults,configBean);
        if (StringUtils.isNotEmpty(twitterResponse.getDetail()) && twitterResponse.getStatus()>0){
            return new Result().error("Twitter sync failed.");
        }
        if (twitterResponse!=null && twitterResponse.getData()!=null){
            int RssTwitterResultCount = 0;
            Map<String, TwitterMedia> mediaMap = new HashMap<>();
            if (twitterResponse.getIncludes() != null && twitterResponse.getIncludes().getMedia() != null) {
                for (TwitterMedia media : twitterResponse.getIncludes().getMedia()) {
                    mediaMap.put(media.getMediaKey(), media);
                }
            }
            for (TwitterData twitterData : twitterResponse.getData()){
                RssTwitterDTO rssTwitterDTO = rssTwitterService.getByTweetsId(twitterData.getId());
                if (rssTwitterDTO == null){
                    rssTwitterDTO = new RssTwitterDTO();
                    rssTwitterDTO.setProjectId(project.getId());
                    rssTwitterDTO.setTweetsId(twitterData.getId());
                    rssTwitterDTO.setContent(twitterData.getText());
                    rssTwitterDTO.setContentPubDate(twitterData.getCreatedAt());
                    if (twitterData.getAttachments() != null && twitterData.getAttachments().getMediaKeys() != null){
                        List<String> mediaUrls = new ArrayList<>();
                        List<String> mediaTypes = new ArrayList<>();
                        for (String mediaKey : twitterData.getAttachments().getMediaKeys()) {
                            TwitterMedia media = mediaMap.get(mediaKey);
//                            if (media != null) {
//                                mediaUrls.add(media.getUrl());
//                                mediaTypes.add(media.getType());
//                            }
                            if (media != null) {
                                String type = media.getType();
                                if ("photo".equals(type)) {
                                    if (media.getUrl() != null) {
                                        mediaUrls.add(media.getUrl());
                                        mediaTypes.add(media.getType());
                                    }
                                } else if ("video".equals(type)) {
                                    String bestVideoUrl = getBestVideoUrl(media.getVariants());
                                    if (bestVideoUrl != null) {
                                        mediaUrls.add(bestVideoUrl);
                                        mediaTypes.add("video");
                                    }
                                }
                            }
                        }
                        if (!mediaUrls.isEmpty()) {
                            rssTwitterDTO.setMediaUrl(String.join(",", mediaUrls));
                            rssTwitterDTO.setMediaType(String.join(",", mediaTypes));
                        }
                    }
                    rssTwitterDTO.setCreateDt(new Date());
                    rssTwitterDTO.setUpdateDt(new Date());
                    RssTwitterResultCount++;
                    rssTwitterService.save(rssTwitterDTO);
                }
            }
            int usedCount = rssTwitterCountInt+RssTwitterResultCount;
            if (usedCount>=30){
                usedCount = 30;
            }
            redisUtils.hSet(CommonConstant.RSS_TWITTER_COUNT,project.getId()+"",usedCount);
            Long count = rssTwitterService.getTodaySyncTweetsCountAndProjectId(project.getId());
            if (count!=null){
                map.put("syncTotal",count);
            }
            map.put("surplusUsage",(30-usedCount));
        }
        return new Result().ok(map);
    }

    @GetMapping("getSyncUsage")
    @ApiOperation("getSyncUsage")
    public Result getSyncUsage(){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        Object rssTwitterCount = redisUtils.hGet(CommonConstant.RSS_TWITTER_COUNT,project.getId()+"");
        int rssTwitterCountInt = rssTwitterCount != null ? Integer.parseInt(rssTwitterCount.toString()) : 0;
        Map<String,Integer> map = new HashMap<>();
        map.put("total",30);
        map.put("usage",rssTwitterCountInt);
        return new Result().ok(map);
    }

    public static String getBestVideoUrl(List<TwitterMediaVariant> variants) {
        if (variants == null || variants.isEmpty()) return null;

        return variants.stream()
                .filter(v -> "video/mp4".equals(v.getContentType()))
                .filter(v -> v.getBitRate() != null)
                .sorted(Comparator.comparingInt(TwitterMediaVariant::getBitRate).reversed())
                .map(TwitterMediaVariant::getUrl)
                .findFirst()
                .orElse(null);
    }
}
