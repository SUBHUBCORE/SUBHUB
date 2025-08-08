package io.subHub.modules.rssTwitter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.rssTwitter.dao.RssTwitterDao;
import io.subHub.modules.rssTwitter.dto.RssTwitterDTO;
import io.subHub.modules.rssTwitter.service.RssTwitterService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.rssTwitter.entity.RssTwitterEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Service
public class RssTwitterServiceImpl extends CrudServiceImpl<RssTwitterDao, RssTwitterEntity, RssTwitterDTO> implements RssTwitterService {

    @Override
    public QueryWrapper<RssTwitterEntity> getWrapper(Map<String, Object> params){
        Long projectId = (Long)params.get("projectId");

        QueryWrapper<RssTwitterEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id", projectId);
        return wrapper;
    }

    @Override
    public RssTwitterDTO getByTweetsId(String tweetsId){
        QueryWrapper<RssTwitterEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("tweets_id",tweetsId);
        RssTwitterEntity entity = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(entity, RssTwitterDTO.class);
    }

    @Override
    public Long getTodaySyncTweetsCountAndProjectId(Long projectId){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        QueryWrapper<RssTwitterEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.ge("create_dt", startOfDay);
        wrapper.lt("create_dt", endOfDay);
        return baseDao.selectCount(wrapper);
    }

}