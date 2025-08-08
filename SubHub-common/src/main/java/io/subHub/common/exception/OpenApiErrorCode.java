package io.subHub.common.exception;

/**
 * Error code, consisting of 5 digits,
 * with the first 2 digits being the module code and the
 * last 3 digits being the business code
 * <p>
 * For example: 10001 (10 represents system modules, 001 represents business codes)
 * </p>
 *
 * @author By
 * @since 1.0.0
 */
public interface OpenApiErrorCode {

    int SEND_LIMIT = 501;

    int ACCOUNT_ERROR = 502;

    int BALANCE_ERROR = 503;

    int SEND_ERROR = 504;
    

}
