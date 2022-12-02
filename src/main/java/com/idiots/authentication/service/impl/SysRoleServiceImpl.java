package com.idiots.authentication.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysMenuMapper;
import com.idiots.authentication.mapper.SysResourceMapper;
import com.idiots.authentication.mapper.SysRoleMapper;
import com.idiots.authentication.entity.*;
import com.idiots.authentication.service.SysRoleMenuService;
import com.idiots.authentication.service.SysRoleResourceService;
import com.idiots.authentication.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台角色管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMenuService roleMenuService;
    @Resource
    private SysRoleResourceService roleResourceService;
    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysResourceMapper resourceMapper;
    @Override
    public boolean create(SysRole role) {
        role.setCreateTime(LocalDateTime.now());
        role.setAccountCount(0);
        role.setSort(0);
        return save(role);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public Page<SysRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<SysRole> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysRole> lambda = wrapper.lambda();
        if(StrUtil.isNotEmpty(keyword)){
            lambda.like(SysRole::getName,keyword);
        }
        return page(page,wrapper);
    }

    @Override
    public List<SysMenu> getMenuList(Long adminId) {
        return menuMapper.getMenuList(adminId);
    }

    @Override
    public List<SysMenu> listMenu(Long roleId) {
        return menuMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<SysResource> listResource(Long roleId) {
        return resourceMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleMenu::getRoleId,roleId);
        roleMenuService.remove(wrapper);
        //批量插入新关系
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu  = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        roleMenuService.saveBatch(roleMenus);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleResource::getRoleId,roleId);
        roleResourceService.remove(wrapper);
        //批量插入新关系
        List<SysRoleResource> roleResources = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            SysRoleResource roleResource  = new SysRoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResources.add(roleResource);
        }
        roleResourceService.saveBatch(roleResources);
        return resourceIds.size();
    }
}
