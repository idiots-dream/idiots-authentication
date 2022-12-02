package com.idiots.authentication.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idiots.authentication.lang.CommonPage;
import com.idiots.authentication.lang.Result;
import com.idiots.authentication.dto.SysAccountLoginParam;
import com.idiots.authentication.dto.SysAccountParam;
import com.idiots.authentication.dto.UpdateAccountPasswordParam;
import com.idiots.authentication.entity.SysAccount;
import com.idiots.authentication.entity.SysRole;
import com.idiots.authentication.service.SysAccountService;
import com.idiots.authentication.service.SysRoleService;
import com.idiots.authentication.util.TokenProviderUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
@Controller
@RequestMapping("/admin")
public class SysAccountController {
    @Resource
    private SysAccountService accountService;
    @Resource
    private SysRoleService roleService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@Validated @RequestBody SysAccountParam SysAccountParam) {
        SysAccount sysAccount = accountService.register(SysAccountParam);
        if (sysAccount == null) {
            return Result.failed("");
        }
        return Result.success(sysAccount);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@Validated @RequestBody SysAccountLoginParam SysAccountLoginParam) {
        String token = accountService.login(SysAccountLoginParam.getUsername(), SysAccountLoginParam.getPassword());
        if (token == null) {
            return Result.failed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", TokenProviderUtil.JWT_AUTH_PREFIX);
        return Result.success(tokenMap);
    }


    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader(TokenProviderUtil.HEADER);
        String refreshToken = accountService.refreshToken(token);
        if (refreshToken == null) {
            return Result.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", TokenProviderUtil.JWT_AUTH_PREFIX);
        return Result.success(tokenMap);
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Result getAdminInfo(Principal principal) {
        if (principal == null) {
            return Result.failed("");
        }
        String username = principal.getName();
        SysAccount sysAccount = accountService.getAccountByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", sysAccount.getUsername());
        data.put("menus", roleService.getMenuList(sysAccount.getId()));
        data.put("icon", sysAccount.getIcon());
        List<SysRole> roleList = accountService.getRoleList(sysAccount.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(SysRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return Result.success(data);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout() {
        return Result.success(null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysAccount> adminList = accountService.list(keyword, pageSize, pageNum);
        return Result.success(CommonPage.restPage(adminList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getItem(@PathVariable Long id) {
        SysAccount admin = accountService.getById(id);
        return Result.success(admin);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id, @RequestBody SysAccount admin) {
        boolean success = accountService.update(id, admin);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(@Validated @RequestBody UpdateAccountPasswordParam updatePasswordParam) {
        int status = accountService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return Result.success(status);
        } else if (status == -1) {
            return Result.failed("提交参数不合法");
        } else if (status == -2) {
            return Result.failed("找不到该用户");
        } else if (status == -3) {
            return Result.failed("旧密码错误");
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        boolean success = accountService.delete(id);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setEnabled(status);
        boolean success = accountService.update(id, sysAccount);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") List<Long> roleIds) {
        int count = accountService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return Result.success(count);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getRoleList(@PathVariable Long adminId) {
        List<SysRole> roleList = accountService.getRoleList(adminId);
        return Result.success(roleList);
    }
}
