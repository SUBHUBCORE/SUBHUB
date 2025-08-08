package io.subHub.modules.rssTwitter.scheduled;

import io.subHub.common.constant.CommonConstant;
import io.subHub.common.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RssTwitterScheduled {
    private Logger logger = LoggerFactory.getLogger(RssTwitterScheduled.class);


    @Resource
    private RedisUtils redisUtils;

    @Scheduled(cron = "0 0 0 1 * ?")//On the 1st of each month at 0:00
    public void resettingSyncUsage() {
        redisUtils.delete(CommonConstant.RSS_TWITTER_COUNT);
    }
}
