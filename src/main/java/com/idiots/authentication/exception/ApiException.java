package com.idiots.authentication.exception;

/**
 * 自定义API异常
 * @author devil-idiots
 * Date 2022-12-2
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
