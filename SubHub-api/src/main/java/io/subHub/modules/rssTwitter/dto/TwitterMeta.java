package io.subHub.modules.rssTwitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwitterMeta {
    @JsonProperty("result_count")
    private int resultCount;
    @JsonProperty("newest_id")
    private String newestId;
    @JsonProperty("oldest_id")
    private String oldestId;
}
