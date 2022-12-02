package com.idiots.authentication.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idiots.authentication.dto.SysAccountParam;
import com.idiots.authentication.dto.UpdateAccountPasswordParam;
import com.idiots.authentication.entity.SysAccount;
import com.idiots.authentication.entity.SysResource;
import com.idiots.authentication.entity.SysRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员管理Service
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysAccountService extends IService<SysAccount> {
    /**
     * 根据用户名获取后台管理员
     */
    SysAccount getAccountByUsername(String username);

    /**
     * 注册功能
     */
    SysAccount register(SysAccountParam SysAccountParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Page<SysAccount> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    boolean update(Long id, SysAccount Account);

    /**
     * 删除指定用户
     */
    boolean delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long AccountId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<SysRole> getRoleList(Long AccountId);

    /**
     * 获取指定用户的可访问资源
     */
    List<SysResource> getResourceList(Long AccountId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAccountPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
