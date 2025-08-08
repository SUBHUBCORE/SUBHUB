package io.subHub.modules.subscription.util;

import io.subHub.modules.subscription.service.SubscriptionService;

import java.util.concurrent.Callable;

public class QuerySubCountThread implements Callable<Integer> {

    private SubscriptionService subscriptionService;
    private Long projectId;


    public QuerySubCountThread(SubscriptionService subscriptionService, Long projectId) {
        this.subscriptionService = subscriptionService;
        this.projectId = projectId;

    }
    @Override
    public Integer call() throws Exception {
        Long subCount = subscriptionService.getSubscriptionCountByProjectId(projectId);
        return subCount.intValue();
    }
}
