package com.idiots.authentication.service.impl;

import com.idiots.authentication.entity.SysAccount;
import com.idiots.authentication.entity.SysResource;
import com.idiots.authentication.service.ISysAuthenticationService;
import com.idiots.authentication.service.SysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author devil-idiots
 * Date 2022-12-03
 * Description
 */
@Slf4j
@Service
public class SysAuthenticationServiceImpl implements ISysAuthenticationService {
    @Resource
    private SysAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        SysAccount account = accountService.getAccountByUsername(username);
        if (account != null) {
            List<SysResource> resourceList = accountService.getResourceList(account.getId());
            account.setResources(resourceList);
            return account;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
