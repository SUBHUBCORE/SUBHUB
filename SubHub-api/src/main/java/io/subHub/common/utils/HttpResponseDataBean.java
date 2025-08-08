package io.subHub.common.utils;

import lombok.Data;

@Data
public class HttpResponseDataBean {
    private String fileURL;
    private String cid;
    private String dm_smid;

    private String dm_subject;
    private String dm_content;
}
