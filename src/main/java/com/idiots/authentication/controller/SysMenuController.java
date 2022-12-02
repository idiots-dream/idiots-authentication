package com.idiots.authentication.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idiots.authentication.lang.CommonPage;
import com.idiots.authentication.lang.Result;
import com.idiots.authentication.dto.SysMenuNode;
import com.idiots.authentication.entity.SysMenu;
import com.idiots.authentication.service.SysMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台菜单管理Controller
 * @author devil-idiots
 * Date 2022-12-2
 */
@Controller
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private SysMenuService menuService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody SysMenu SysMenu) {
        boolean success = menuService.create(SysMenu);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id,
                               @RequestBody SysMenu SysMenu) {
        boolean success = menuService.update(id, SysMenu);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getItem(@PathVariable Long id) {
        SysMenu SysMenu = menuService.getById(id);
        return Result.success(SysMenu);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        boolean success = menuService.removeById(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }


    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@PathVariable Long parentId,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return Result.success(CommonPage.restPage(menuList));
    }

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public Result treeList() {
        List<SysMenuNode> list = menuService.treeList();
        return Result.success(list);
    }

    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        boolean success = menuService.updateHidden(id, hidden);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }
}
