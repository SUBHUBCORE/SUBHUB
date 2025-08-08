package io.subHub.modules.rssTwitter.service;

import io.subHub.modules.rssTwitter.dto.RssTwitterDTO;
import io.subHub.common.service.CrudService;
import io.subHub.modules.rssTwitter.entity.RssTwitterEntity;

public interface RssTwitterService extends CrudService<RssTwitterEntity, RssTwitterDTO> {

    RssTwitterDTO getByTweetsId(String tweetsId);

    Long getTodaySyncTweetsCountAndProjectId(Long projectId);

}