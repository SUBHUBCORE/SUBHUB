package io.subHub.modules.rssTwitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class TwitterAttachments {
    @JsonProperty("media_keys")
    private List<String> mediaKeys;
}
