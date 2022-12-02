package com.idiots.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idiots.authentication.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据后台用户ID获取菜单
     */
    List<SysMenu> getMenuList(@Param("accountId") Long accountId);
    /**
     * 根据角色ID获取菜单
     */
    List<SysMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

}
