package com.idiots.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysAccountRoleMapper;
import com.idiots.authentication.entity.SysAccountRole;
import com.idiots.authentication.service.SysAccountRoleService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysAccountRoleServiceImpl extends ServiceImpl<SysAccountRoleMapper, SysAccountRole> implements SysAccountRoleService {
}
