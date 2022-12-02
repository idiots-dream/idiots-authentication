package com.idiots.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idiots.authentication.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取用户所有角色
     */
    List<SysRole> getRoleList(@Param("accountId") Long accountId);

}
