package com.idiots.authentication.controller;

import com.idiots.authentication.lang.Result;
import com.idiots.authentication.entity.SysResourceCategory;
import com.idiots.authentication.service.SysResourceCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台资源分类管理Controller
 * @author devil-idiots
 * Date 2022-12-2
 */
@Controller
@RequestMapping("/resourceCategory")
public class SysResourceCategoryController {
    @Resource
    private SysResourceCategoryService resourceCategoryService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public Result listAll() {
        List<SysResourceCategory> resourceList = resourceCategoryService.listAll();
        return Result.success(resourceList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody SysResourceCategory SysResourceCategory) {
        boolean success = resourceCategoryService.create(SysResourceCategory);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id, @RequestBody SysResourceCategory SysResourceCategory) {
        SysResourceCategory.setId(id);
        boolean success = resourceCategoryService.updateById(SysResourceCategory);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.failed("");
        }
    }
}
