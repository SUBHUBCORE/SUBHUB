package io.subHub.modules.subscription.util;

import io.subHub.modules.subscription.service.SubscriptionService;

import java.util.*;
import java.util.concurrent.Callable;

public class QuerySubNetworkCountThread implements Callable<List<Map<String, Object>>> {

    private SubscriptionService subscriptionService;
    private Long projectId;

    public QuerySubNetworkCountThread(SubscriptionService subscriptionService, Long projectId) {
        this.subscriptionService = subscriptionService;
        this.projectId = projectId;

    }
    @Override
    public  List<Map<String, Object>> call() throws Exception {
        List<Map<String, Object>> subscriberNetworkCountList= subscriptionService.getSubscriberNetworkCountByProjectId(projectId);
        Iterator<Map<String, Object>> iterator = subscriberNetworkCountList.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            // Determine if the deletion criteria are met
            if (map.containsKey("Unkown") || map.containsKey("Default") || map.containsKey("") || map.containsKey("null")){
                iterator.remove();
            }
        }
        return subscriberNetworkCountList;
    }

}
