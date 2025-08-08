package io.subHub.common.utils;

import lombok.Data;

@Data
public class HttpCommonResponseBean {
    private int code;
    private boolean success;
    private Object data;
    private String msg;
}
