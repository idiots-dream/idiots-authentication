package com.idiots.authentication.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.domain.AccountUserDetails;
import com.idiots.authentication.dto.SysAccountParam;
import com.idiots.authentication.dto.UpdateAccountPasswordParam;
import com.idiots.authentication.exception.Asserts;
import com.idiots.authentication.mapper.SysAccountMapper;
import com.idiots.authentication.mapper.SysLoginLogMapper;
import com.idiots.authentication.mapper.SysResourceMapper;
import com.idiots.authentication.mapper.SysRoleMapper;
import com.idiots.authentication.entity.*;
import com.idiots.authentication.service.SysAccountRoleService;
import com.idiots.authentication.service.SysAccountService;
import com.idiots.authentication.util.TokenProviderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理员管理Service实现类
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
@Slf4j
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysLoginLogMapper loginLogMapper;
    @Resource
    private SysAccountRoleService accountRoleService;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysResourceMapper resourceMapper;

    @Override
    public SysAccount getAccountByUsername(String username) {
        QueryWrapper<SysAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysAccount::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public SysAccount register(SysAccountParam sysAccountParam) {
        SysAccount sysAccount = new SysAccount();
        BeanUtils.copyProperties(sysAccountParam, sysAccount);
        sysAccount.setCreatedTime(LocalDateTime.now());
        sysAccount.setEnabled(1);
        //查询是否有相同用户名的用户
        QueryWrapper<SysAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysAccount::getUsername, sysAccount.getUsername());
        List<SysAccount> sysAccountList = list(wrapper);
        if (sysAccountList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(sysAccount.getPassword());
        sysAccount.setPassword(encodePassword);
        baseMapper.insert(sysAccount);
        return sysAccount;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = TokenProviderUtil.token(username);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        SysAccount Account = getAccountByUsername(username);
        if (Account == null) {
            return;
        }
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setAccountId(Account.getId());
        loginLog.setLoginTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        SysAccount record = new SysAccount();
        record.setLoginTime(LocalDateTime.now());
        QueryWrapper<SysAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysAccount::getUsername, username);
        update(record, wrapper);
    }

    @Override
    public String refreshToken(String oldToken) {
        return TokenProviderUtil.token(oldToken);
    }

    @Override
    public Page<SysAccount> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<SysAccount> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysAccount> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysAccount> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(SysAccount::getUsername, keyword);
            lambda.or().like(SysAccount::getNickName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean update(Long id, SysAccount Account) {
        Account.setId(id);
        SysAccount rawAccount = getById(id);
        if (rawAccount.getPassword().equals(Account.getPassword())) {
            //与原加密密码相同的不需要修改
            Account.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(Account.getPassword())) {
                Account.setPassword(null);
            } else {
                Account.setPassword(passwordEncoder.encode(Account.getPassword()));
            }
        }
        boolean success = updateById(Account);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public int updateRole(Long AccountId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<SysAccountRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysAccountRole::getAccountId, AccountId);
        accountRoleService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysAccountRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SysAccountRole roleAccount = new SysAccountRole();
                roleAccount.setAccountId(AccountId);
                roleAccount.setRoleId(roleId);
                list.add(roleAccount);
            }
            accountRoleService.saveBatch(list);
        }
        return count;
    }

    @Override
    public List<SysRole> getRoleList(Long AccountId) {
        return roleMapper.getRoleList(AccountId);
    }

    @Override
    public List<SysResource> getResourceList(Long AccountId) {
        List<SysResource> resourceList = resourceMapper.getResourceList(AccountId);
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAccountPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        QueryWrapper<SysAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysAccount::getUsername, param.getUsername());
        List<SysAccount> AccountList = list(wrapper);
        if (CollUtil.isEmpty(AccountList)) {
            return -2;
        }
        SysAccount SysAccount = AccountList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), SysAccount.getPassword())) {
            return -3;
        }
        SysAccount.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(SysAccount);
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        SysAccount account = getAccountByUsername(username);
        if (account != null) {
            List<SysResource> resourceList = getResourceList(account.getId());
            return new AccountUserDetails(account, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
