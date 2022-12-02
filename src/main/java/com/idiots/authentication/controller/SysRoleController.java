package com.idiots.authentication.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idiots.authentication.lang.CommonPage;
import com.idiots.authentication.lang.Result;
import com.idiots.authentication.entity.SysMenu;
import com.idiots.authentication.entity.SysResource;
import com.idiots.authentication.entity.SysRole;
import com.idiots.authentication.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台用户角色管理
 * @author devil-idiots
 * Date 2022-12-2
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {
    @Resource
    private SysRoleService roleService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody SysRole role) {
        boolean success = roleService.create(role);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        boolean success = roleService.updateById(role);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.delete(ids);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }


    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public Result listAll() {
        List<SysRole> roleList = roleService.list();
        return Result.success(roleList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysRole> roleList = roleService.list(keyword, pageSize, pageNum);
        return Result.success(CommonPage.restPage(roleList));
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        SysRole SysRole = new SysRole();
        SysRole.setId(id);
        SysRole.setStatus(status);
        boolean success = roleService.updateById(SysRole);
        if (success) {
            return Result.success(null);
        }
        return Result.failed("");
    }

    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result listMenu(@PathVariable Long roleId) {
        List<SysMenu> roleList = roleService.listMenu(roleId);
        return Result.success(roleList);
    }


    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result listResource(@PathVariable Long roleId) {
        List<SysResource> roleList = roleService.listResource(roleId);
        return Result.success(roleList);
    }


    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public Result allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return Result.success(count);
    }

    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public Result allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return Result.success(count);
    }

}
