package com.idiots.authentication.exception;

/**
 * 断言处理类，用于抛出各种API异常
 * @author devil-idiots
 * Date 2022-12-2
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }
}
