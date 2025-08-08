package io.subHub.modules.subscription.util;

import io.subHub.modules.subscription.service.SubscriptionService;

import java.util.concurrent.Callable;

public class QueryInviteSubCountThread implements Callable<Integer> {

    private SubscriptionService subscriptionService;
    private Long projectId;


    public QueryInviteSubCountThread(SubscriptionService subscriptionService, Long projectId) {
        this.subscriptionService = subscriptionService;
        this.projectId = projectId;

    }
    @Override
    public Integer call() throws Exception {
        Long invitedSubscribersCount = subscriptionService.getInviteSubscriptionCountByProjectId(projectId);
        return invitedSubscribersCount.intValue();
    }
}
