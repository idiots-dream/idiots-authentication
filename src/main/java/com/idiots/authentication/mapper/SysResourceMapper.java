package com.idiots.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idiots.authentication.entity.SysResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 获取用户所有可访问资源
     */
    List<SysResource> getResourceList(@Param("accountId") Long accountId);

    /**
     * 根据角色ID获取资源
     */
    List<SysResource> getResourceListByRoleId(@Param("roleId") Long roleId);

}
