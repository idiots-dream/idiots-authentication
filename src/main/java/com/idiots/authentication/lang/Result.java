package com.idiots.authentication.lang;

import lombok.Data;

/**
 * 通用返回对象
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
@Data
public class Result {
    private long code;
    private String message;
    private Object data;


    protected Result(long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static Result success(Object data) {
        return new Result(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMessage(), data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static Result failed(String message) {
        return new Result(RespCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }
}
