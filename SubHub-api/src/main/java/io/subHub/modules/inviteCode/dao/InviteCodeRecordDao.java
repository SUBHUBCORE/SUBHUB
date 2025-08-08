package io.subHub.modules.inviteCode.dao;

import io.subHub.modules.inviteCode.entity.InviteCodeRecordEntity;
import io.subHub.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * Invitation Code Record
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Mapper
public interface InviteCodeRecordDao extends BaseDao<InviteCodeRecordEntity> {
	
}