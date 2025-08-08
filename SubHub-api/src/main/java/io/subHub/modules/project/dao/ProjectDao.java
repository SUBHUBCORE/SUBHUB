package io.subHub.modules.project.dao;

import io.subHub.common.dao.BaseDao;
import io.subHub.modules.project.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Mapper
public interface ProjectDao extends BaseDao<ProjectEntity> {

    String getNftByUserId(Long userId);


	
}