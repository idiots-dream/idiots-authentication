package com.idiots.authentication.resp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idiots.authentication.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author devil-idiots
 * Date 2022-12-03
 * Description
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.idiots.authentication")
public class IResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter param, @NotNull Class clazz) {
        log.info("参数==:{}", param);
        log.info("clazz==:{}", clazz);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter param, @NotNull MediaType type, @NotNull Class clazz, @NotNull ServerHttpRequest req, @NotNull ServerHttpResponse resp) {
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        if (body instanceof Result) {
            return body;
        }
        // 如果返回值是String类型，那就手动把Result对象转换成JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.success(body);
    }
}
