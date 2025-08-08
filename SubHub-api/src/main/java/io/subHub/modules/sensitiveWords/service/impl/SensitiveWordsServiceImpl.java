package io.subHub.modules.sensitiveWords.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.sensitiveWords.service.SensitiveWordsService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.sensitiveWords.dao.SensitiveWordsDao;
import io.subHub.modules.sensitiveWords.dto.SensitiveWordsDTO;
import io.subHub.modules.sensitiveWords.entity.SensitiveWordsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Service
public class SensitiveWordsServiceImpl extends CrudServiceImpl<SensitiveWordsDao, SensitiveWordsEntity, SensitiveWordsDTO> implements SensitiveWordsService {

    @Override
    public QueryWrapper<SensitiveWordsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SensitiveWordsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<SensitiveWordsDTO> getSensitiveWordsAll(){
        QueryWrapper<SensitiveWordsEntity> wrapper = new QueryWrapper<>();
        List<SensitiveWordsEntity> entity = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(entity,SensitiveWordsDTO.class);
    }
}