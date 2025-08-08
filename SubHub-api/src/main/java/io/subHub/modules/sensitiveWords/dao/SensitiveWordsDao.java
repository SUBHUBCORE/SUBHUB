package io.subHub.modules.sensitiveWords.dao;

import io.subHub.modules.sensitiveWords.entity.SensitiveWordsEntity;
import io.subHub.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SensitiveWordsDao extends BaseDao<SensitiveWordsEntity> {
	
}