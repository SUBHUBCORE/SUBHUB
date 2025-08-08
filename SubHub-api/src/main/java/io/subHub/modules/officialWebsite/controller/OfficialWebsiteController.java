package io.subHub.modules.officialWebsite.controller;

import io.subHub.common.constant.CommonConstant;
import io.subHub.common.redis.RedisUtils;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.common.utils.Result;
import io.subHub.modules.officialWebsite.dto.OfficialWebsiteDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("officialWebsite")
public class OfficialWebsiteController {
    private Logger logger = LoggerFactory.getLogger(OfficialWebsiteController.class);

    @Resource
    private RedisUtils redisUtils;


    @GetMapping("getSubHubData")
    public Result getMessageDetails(){
        Object data = redisUtils.get(CommonConstant.OFFICIAL_WEBSITE_DATA);
        OfficialWebsiteDataDTO officialWebsiteData = null;
        if (data != null){
            officialWebsiteData = ConvertUtils.sourceToTarget(data, OfficialWebsiteDataDTO.class);
        }else {
            officialWebsiteData = new OfficialWebsiteDataDTO();
        }
        return new Result().ok(officialWebsiteData);
    }
}
