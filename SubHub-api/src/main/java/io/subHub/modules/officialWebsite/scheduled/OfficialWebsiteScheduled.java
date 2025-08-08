package io.subHub.modules.officialWebsite.scheduled;

import io.subHub.modules.message.service.MessageService;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.common.constant.CommonConstant;
import io.subHub.common.redis.RedisUtils;
import io.subHub.modules.officialWebsite.dto.OfficialWebsiteDataDTO;
import io.subHub.modules.subscription.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OfficialWebsiteScheduled {
    private Logger logger = LoggerFactory.getLogger(OfficialWebsiteScheduled.class);


    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ProjectService projectService;
    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private MessageService messageService;

    @Scheduled(cron = "0 0/10 * * * ?") // Execute once every 10 minutes
    public void checkNodeOrder() {
        Long projectCount = projectService.getProjectCount();
        Long subscriberCount = subscriptionService.getSubscriberCount();
        Long messageCount  =messageService.getMessageSendSum();
        OfficialWebsiteDataDTO dto = new OfficialWebsiteDataDTO();
        dto.setOnboardedProjects(projectCount);
        dto.setSubscriberCount(subscriberCount);
        dto.setMessagesCount(messageCount);
        redisUtils.set(CommonConstant.OFFICIAL_WEBSITE_DATA,dto);
    }
}
