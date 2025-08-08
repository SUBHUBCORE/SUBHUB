package io.subHub.modules.rssMedium.service;

import io.subHub.modules.rssMedium.dto.RssMediumDTO;
import io.subHub.modules.rssMedium.entity.RssMediumEntity;
import io.subHub.common.service.CrudService;

public interface RssMediumService extends CrudService<RssMediumEntity, RssMediumDTO> {

    RssMediumDTO getByProjectIdAndGuid(Long projectId,String guid);

    Long getCountByProject(Long projectId);

}