package io.subHub.modules.walletsAgent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.walletsAgent.service.WalletsAgentService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.modules.walletsAgent.dao.WalletsAgentDao;
import io.subHub.modules.walletsAgent.dto.WalletsAgentDTO;
import io.subHub.modules.walletsAgent.entity.WalletsAgentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-02-27
 */
@Service
public class WalletsAgentServiceImpl extends CrudServiceImpl<WalletsAgentDao, WalletsAgentEntity, WalletsAgentDTO> implements WalletsAgentService {

    @Override
    public QueryWrapper<WalletsAgentEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WalletsAgentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public  WalletsAgentEntity getUserPidByAddress(String address){
        QueryWrapper<WalletsAgentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("src_address",address);
        return baseDao.selectOne(wrapper);
    }

}