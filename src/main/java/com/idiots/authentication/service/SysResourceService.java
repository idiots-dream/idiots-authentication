package com.idiots.authentication.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idiots.authentication.entity.SysResource;

/**
 * 后台资源管理Service
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysResourceService extends IService<SysResource> {
    /**
     * 添加资源
     */
    boolean create(SysResource SysResource);

    /**
     * 修改资源
     */
    boolean update(Long id, SysResource SysResource);

    /**
     * 删除资源
     */
    boolean delete(Long id);

    /**
     * 分页查询资源
     */
    Page<SysResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
