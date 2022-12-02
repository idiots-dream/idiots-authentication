package com.idiots.authentication.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 修改用户名密码参数
 * @author devil-idiots
 * Date 2022-12-2
 */
@Getter
@Setter
public class UpdateAccountPasswordParam {
    @NotEmpty

    private String username;
    @NotEmpty

    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
