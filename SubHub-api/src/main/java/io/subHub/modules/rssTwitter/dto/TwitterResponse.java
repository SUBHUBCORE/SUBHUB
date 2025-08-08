package io.subHub.modules.rssTwitter.dto;

import lombok.Data;

import java.util.List;

@Data
public class TwitterResponse {
    private List<TwitterData> data;
    private TwitterDataIncludes includes;
    private TwitterMeta meta;
    private String title;
    private String type;
    private Integer status;
    private String detail;

}
