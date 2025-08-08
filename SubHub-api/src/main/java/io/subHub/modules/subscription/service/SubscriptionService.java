package io.subHub.modules.subscription.service;

import io.subHub.common.service.CrudService;
import io.subHub.modules.subscription.dto.SubscriptionDTO;
import io.subHub.modules.subscription.entity.SubscriptionEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
public interface SubscriptionService extends CrudService<SubscriptionEntity, SubscriptionDTO> {

    Long getSubscriptionCountByProjectId(Long projectId);

    Long getInviteSubscriptionCountByProjectId(Long projectId);

    List<SubscriptionDTO> getSubscriptionListByProjectId(Long projectId);

    List<SubscriptionDTO> getSubscriptionListByAddress(String[] address,Long projectId);

    Long getThisDaySubscriptionCountByProjectId(Long projectId);

    List<Map<String,Object>> getSubscriberCountByDay(Long projectId,int day);

    List<Map<String,Object>> getSubscriberNetworkCountByProjectId(Long projectId);


    SubscriptionDTO getSubscriptionByAddressAndProjectId(Map<String, Object> params);

    List<SubscriptionDTO> getSubscriberByProjectIdPage(Long projectId,int page,int pageSize);

    List<SubscriptionEntity> getSubscriberByProjectIdPage1(Long projectId,int page,int pageSize);

    Long getSubscriberCount();

    void updateByAddress(Long projectId,String address);
}