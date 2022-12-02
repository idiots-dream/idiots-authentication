package com.idiots.authentication.lang;

/**
 * 枚举了一些常用API操作码
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
public enum RespCode {
    /**
     * 请求成功
     */
    SUCCESS(0, "请求成功"),
    /**
     * 请求失败
     */
    BAD_REQUEST(400, "请求失败"),
    /**
     * 请求资源不存在
     */
    NOT_FOUND(404, "请求资源不存在"),
    /**
     * 请求方式不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方式不允许"),
    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408,"请求超时"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
    /**
     * 远程服务器接收到了一个无效的响应
     */
    BAD_GATEWAY(502,"远程服务器接收到了一个无效的响应"),
    /**
     * 网关响应超时
     */
    GATEWAY_TIMEOUT(504,"网关响应超时")
    ;


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;

    RespCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
