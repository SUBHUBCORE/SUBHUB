package io.subHub.modules.rssMedium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.rssMedium.dao.RssMediumDao;
import io.subHub.modules.rssMedium.dto.RssMediumDTO;
import io.subHub.modules.rssMedium.entity.RssMediumEntity;
import io.subHub.modules.rssMedium.service.RssMediumService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Service
public class RssMediumServiceImpl extends CrudServiceImpl<RssMediumDao, RssMediumEntity, RssMediumDTO> implements RssMediumService {

    @Override
    public QueryWrapper<RssMediumEntity> getWrapper(Map<String, Object> params){
        Long projectId = (Long)params.get("projectId");

        QueryWrapper<RssMediumEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id", projectId);
        return wrapper;
    }

    @Override
    public RssMediumDTO getByProjectIdAndGuid(Long projectId,String guid){
        QueryWrapper<RssMediumEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.eq("guid",guid);
        RssMediumEntity entity = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(entity, RssMediumDTO.class);
    }

    @Override
    public Long getCountByProject(Long projectId){
        QueryWrapper<RssMediumEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        return baseDao.selectCount(wrapper);
    }

}