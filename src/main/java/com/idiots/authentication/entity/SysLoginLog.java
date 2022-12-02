package com.idiots.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author devil-idiots
 * @since 2022-12-02
 */
@Getter
@Setter
@TableName("sys_login_log")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号ID
     */
    private Long accountId;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    private String ip;

    private String address;

    /**
     * 浏览器登录类型
     */
    private String userAgent;

    /**
     * 登录状态（成功/失败）
     */
    private String status;
}
