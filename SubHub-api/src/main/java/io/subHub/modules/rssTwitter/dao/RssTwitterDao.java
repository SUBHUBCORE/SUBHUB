package io.subHub.modules.rssTwitter.dao;

import io.subHub.common.dao.BaseDao;
import io.subHub.modules.rssTwitter.entity.RssTwitterEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RssTwitterDao extends BaseDao<RssTwitterEntity> {
	
}