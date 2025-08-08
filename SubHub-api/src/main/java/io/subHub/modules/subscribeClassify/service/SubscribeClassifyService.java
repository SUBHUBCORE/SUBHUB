package io.subHub.modules.subscribeClassify.service;

import io.subHub.modules.subscribeClassify.dto.SubscribeClassifyDTO;
import io.subHub.common.service.CrudService;
import io.subHub.modules.subscribeClassify.entity.SubscribeClassifyEntity;

import java.util.List;

/**
 *
 * @author by 
 * @since 1.0.0 2024-02-22
 */
public interface SubscribeClassifyService extends CrudService<SubscribeClassifyEntity, SubscribeClassifyDTO> {
    List<SubscribeClassifyDTO> getAllSubscribeClassify();

}