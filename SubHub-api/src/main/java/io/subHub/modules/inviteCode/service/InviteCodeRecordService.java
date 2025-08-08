package io.subHub.modules.inviteCode.service;

import io.subHub.modules.inviteCode.dto.InviteCodeRecordDTO;
import io.subHub.modules.inviteCode.entity.InviteCodeRecordEntity;
import io.subHub.common.service.CrudService;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
public interface InviteCodeRecordService extends CrudService<InviteCodeRecordEntity, InviteCodeRecordDTO> {

    InviteCodeRecordEntity getByInviteCode(String code);

    InviteCodeRecordDTO getByUserId(Long userId);

    void updateByUseUserId(Long userId);

}