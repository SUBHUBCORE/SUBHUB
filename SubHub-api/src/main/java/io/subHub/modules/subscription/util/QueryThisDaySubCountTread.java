package io.subHub.modules.subscription.util;

import io.subHub.modules.subscription.service.SubscriptionService;

import java.util.concurrent.Callable;

public class QueryThisDaySubCountTread implements Callable<Integer> {

    private SubscriptionService subscriptionService;
    private Long projectId;


    public QueryThisDaySubCountTread(SubscriptionService subscriptionService, Long projectId) {
        this.subscriptionService = subscriptionService;
        this.projectId = projectId;

    }
    @Override
    public Integer call() throws Exception {
        Long ThisDaySubscriptionCount = subscriptionService.getThisDaySubscriptionCountByProjectId(projectId);
        return ThisDaySubscriptionCount.intValue();
    }
}
