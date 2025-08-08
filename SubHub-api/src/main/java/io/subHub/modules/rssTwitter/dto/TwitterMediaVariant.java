package io.subHub.modules.rssTwitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwitterMediaVariant {
    @JsonProperty("bit_rate")
    private Integer bitRate;

    @JsonProperty("content_type")
    private String contentType;

    private String url;
}
