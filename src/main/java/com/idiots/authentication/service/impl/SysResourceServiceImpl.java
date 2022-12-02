package com.idiots.authentication.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysResourceMapper;
import com.idiots.authentication.entity.SysResource;
import com.idiots.authentication.service.SysResourceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 后台资源管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {
    @Override
    public boolean create(SysResource SysResource) {
        SysResource.setCreateTime(LocalDateTime.now());
        return save(SysResource);
    }

    @Override
    public boolean update(Long id, SysResource SysResource) {
        SysResource.setId(id);
        boolean success = updateById(SysResource);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public Page<SysResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Page<SysResource> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysResource> lambda = wrapper.lambda();
        if(categoryId!=null){
            lambda.eq(SysResource::getCategoryId,categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            lambda.like(SysResource::getName,nameKeyword);
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            lambda.like(SysResource::getUrl,urlKeyword);
        }
        return page(page,wrapper);
    }
}
