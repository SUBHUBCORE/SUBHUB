package io.subHub.modules.sensitiveWords.service;

import io.subHub.common.service.CrudService;
import io.subHub.modules.sensitiveWords.dto.SensitiveWordsDTO;
import io.subHub.modules.sensitiveWords.entity.SensitiveWordsEntity;

import java.util.List;

public interface SensitiveWordsService extends CrudService<SensitiveWordsEntity, SensitiveWordsDTO> {

    List<SensitiveWordsDTO> getSensitiveWordsAll();
}