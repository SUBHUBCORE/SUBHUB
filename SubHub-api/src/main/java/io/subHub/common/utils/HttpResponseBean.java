package io.subHub.common.utils;

import lombok.Data;

@Data
public class HttpResponseBean {
    private int code;
    private String message;
    private boolean success;
    private HttpResponseDataBean data;
    private String msg;

}
