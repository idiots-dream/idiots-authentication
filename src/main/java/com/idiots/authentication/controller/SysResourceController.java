package com.idiots.authentication.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idiots.authentication.lang.CommonPage;
import com.idiots.authentication.lang.Result;
import com.idiots.authentication.entity.SysResource;
import com.idiots.authentication.security.DynamicSecurityMetadataSource;
import com.idiots.authentication.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源管理Controller
 * @author devil-idiots
 * Date 2022-12-2
 */
@Controller
@RequestMapping("/resource")
public class SysResourceController {

    @Autowired
    private SysResourceService resourceService;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody SysResource SysResource) {
        boolean success = resourceService.create(SysResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id,
                               @RequestBody SysResource SysResource) {
        boolean success = resourceService.update(id, SysResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getItem(@PathVariable Long id) {
        SysResource SysResource = resourceService.getById(id);
        return Result.success(SysResource);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        boolean success = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) String nameKeyword,
                                                      @RequestParam(required = false) String urlKeyword,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysResource> resourceList = resourceService.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return Result.success(CommonPage.restPage(resourceList));
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public Result listAll() {
        List<SysResource> resourceList = resourceService.list();
        return Result.success(resourceList);
    }
}
