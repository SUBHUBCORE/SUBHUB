package io.subHub.modules.subscribeClassify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.subscribeClassify.dao.SubscribeClassifyDao;
import io.subHub.modules.subscribeClassify.dto.SubscribeClassifyDTO;
import io.subHub.modules.subscribeClassify.service.SubscribeClassifyService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.subscribeClassify.entity.SubscribeClassifyEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author by 
 * @since 1.0.0 2024-02-22
 */
@Service
public class SubscribeClassifyServiceImpl extends CrudServiceImpl<SubscribeClassifyDao, SubscribeClassifyEntity, SubscribeClassifyDTO> implements SubscribeClassifyService {

    @Override
    public QueryWrapper<SubscribeClassifyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SubscribeClassifyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<SubscribeClassifyDTO> getAllSubscribeClassify(){
        QueryWrapper<SubscribeClassifyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0);
        wrapper.orderByDesc("sort");
        return ConvertUtils.sourceToTarget(baseDao.selectList(wrapper), SubscribeClassifyDTO.class);

    }

}