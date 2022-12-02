package com.idiots.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.idiots.authentication.entity.SysResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysResourceCategoryService extends IService<SysResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<SysResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(SysResourceCategory SysResourceCategory);
}
