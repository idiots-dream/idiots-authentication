package com.idiots.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysRoleMenuMapper;
import com.idiots.authentication.entity.SysRoleMenu;
import com.idiots.authentication.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
}
