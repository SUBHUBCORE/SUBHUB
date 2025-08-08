package io.subHub.modules.rssTwitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TwitterMedia {
    @JsonProperty("media_key")
    private String mediaKey;
    private String type;
    private String url;
    private List<TwitterMediaVariant> variants;
}
