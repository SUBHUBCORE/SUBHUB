package io.subHub.modules.walletsAgent.service;

import io.subHub.common.service.CrudService;
import io.subHub.modules.walletsAgent.dto.WalletsAgentDTO;
import io.subHub.modules.walletsAgent.entity.WalletsAgentEntity;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-02-27
 */
public interface WalletsAgentService extends CrudService<WalletsAgentEntity, WalletsAgentDTO> {


    WalletsAgentEntity getUserPidByAddress(String address);
}