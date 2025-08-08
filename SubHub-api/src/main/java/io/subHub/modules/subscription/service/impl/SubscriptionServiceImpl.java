package io.subHub.modules.subscription.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.subHub.modules.subscription.service.SubscriptionService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.subscription.dao.SubscriptionDao;
import io.subHub.modules.subscription.dto.SubscriptionDTO;
import io.subHub.modules.subscription.entity.SubscriptionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
@Service
public class SubscriptionServiceImpl extends CrudServiceImpl<SubscriptionDao, SubscriptionEntity, SubscriptionDTO> implements SubscriptionService {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Override
    public QueryWrapper<SubscriptionEntity> getWrapper(Map<String, Object> params){
        Long projectId = (Long)params.get("projectId");
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id", projectId);
        wrapper.eq("subscribe_status",0);
        return wrapper;
    }

    @Override
    public Long getSubscriptionCountByProjectId(Long projectId){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id",projectId);
        wrapper.eq("subscribe_status",0);
        Long count = baseDao.selectCount(wrapper);
        return count;
    }

    @Override
    public Long getInviteSubscriptionCountByProjectId(Long projectId){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id",projectId);
        wrapper.eq("subscribe_source",1);
        Long count = baseDao.selectCount(wrapper);
        return count;
    }

    @Override
    public List<SubscriptionDTO> getSubscriptionListByProjectId(Long projectId){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id",projectId);
        wrapper.eq("subscribe_status",0);
        List<SubscriptionEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list, SubscriptionDTO.class);
    }

    @Override
    public List<SubscriptionDTO> getSubscriptionListByAddress(String[] address,Long projectId){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.in("address",address);
        wrapper.eq("belongs_to_id",projectId);
        wrapper.eq("subscribe_status",0);
        List<SubscriptionEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list, SubscriptionDTO.class);
    }

    @Override
    public Long getThisDaySubscriptionCountByProjectId(Long projectId){
        return subscriptionDao.getThisDaySubCountByProjectId(projectId);
    }

    @Override
    public List<Map<String,Object>> getSubscriberCountByDay(Long projectId,int day){
        List<Map<String, Object>> list = subscriptionDao.getSubscriberCountByDay(projectId,day);
        return list;
    }
    @Override
    public List<Map<String,Object>> getSubscriberNetworkCountByProjectId(Long projectId){
        List<Map<String,Object>> list = subscriptionDao.getSubscriberNetworkCountByProjectId(projectId);
        return list;
    }


    @Override
    public SubscriptionDTO getSubscriptionByAddressAndProjectId(Map<String, Object> params){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("address",params.get("address"));
        wrapper.eq("belongs_to_id",params.get("appId"));
        SubscriptionEntity entity = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(entity, SubscriptionDTO.class);
    }

    @Override
    public List<SubscriptionDTO> getSubscriberByProjectIdPage(Long projectId,int page,int pageSize){
        return subscriptionDao.getSubscriberByProjectIdPage(projectId,page,pageSize);
    }

    @Override
    public Long getSubscriberCount(){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("subscribe_status",0);
        return baseDao.selectCount(wrapper);
    }

    @Override
    public List<SubscriptionEntity> getSubscriberByProjectIdPage1(Long projectId,int page,int pageSize){
        QueryWrapper<SubscriptionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id",projectId);
        wrapper.eq("subscribe_status",0);
        wrapper.orderByAsc("id");
        wrapper.last("LIMIT "+page+","+pageSize);
        return baseDao.selectList(wrapper);

    }

    @Override
    public void updateByAddress(Long projectId,String address){
        UpdateWrapper<SubscriptionEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("belongs_to_id",projectId);
        updateWrapper.eq("address",address);
        updateWrapper.set("subscribe_status",1);
        updateWrapper.set("update_at",new Date());
        baseDao.update(new SubscriptionEntity(),updateWrapper);
    }

}