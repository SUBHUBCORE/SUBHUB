package io.subHub.modules.rssTwitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TwitterData {
    private String id;

    private String text;

    @JsonProperty("edit_history_tweet_ids")
    private List<String> editHistoryTweetIds;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("attachments")
    private TwitterAttachments attachments;
}
