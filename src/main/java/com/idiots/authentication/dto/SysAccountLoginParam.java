package com.idiots.authentication.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * @author devil-idiots
 * Date 2022-12-2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAccountLoginParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
