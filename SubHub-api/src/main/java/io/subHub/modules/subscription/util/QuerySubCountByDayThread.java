package io.subHub.modules.subscription.util;

import io.subHub.modules.subscription.service.SubscriptionService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class QuerySubCountByDayThread implements Callable<List<Map<String, Object>>> {

    private SubscriptionService subscriptionService;
    private Long projectId;
    private int day;

    public QuerySubCountByDayThread(SubscriptionService subscriptionService, Long projectId,int day) {
        this.subscriptionService = subscriptionService;
        this.projectId = projectId;
        this.day = day;

    }
    @Override
    public  List<Map<String, Object>> call() throws Exception {
        List<Map<String, Object>> subscriberCountDayList = subscriptionService.getSubscriberCountByDay(projectId,day);
        return subscriberCountDayList;
    }
}
