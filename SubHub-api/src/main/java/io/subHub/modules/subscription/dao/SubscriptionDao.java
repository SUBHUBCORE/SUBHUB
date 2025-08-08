package io.subHub.modules.subscription.dao;

import io.subHub.modules.subscription.dto.SubscriptionDTO;
import io.subHub.modules.subscription.entity.SubscriptionEntity;
import io.subHub.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
@Mapper
public interface SubscriptionDao extends BaseDao<SubscriptionEntity> {

    Long getThisDaySubCountByProjectId(Long projectId);

    List<Map<String,Object>> getSubscriberCountByDay(Long projectId,int day);

    List<Map<String,Object>> getSubscriberNetworkCountByProjectId(Long projectId);

    List<SubscriptionDTO> getSubscriberByProjectIdPage(Long projectId, int page, int pageSize);

}