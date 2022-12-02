package com.idiots.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysRoleResourceMapper;
import com.idiots.authentication.entity.SysRoleResource;
import com.idiots.authentication.service.SysRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * 角色资源关系管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {
}
