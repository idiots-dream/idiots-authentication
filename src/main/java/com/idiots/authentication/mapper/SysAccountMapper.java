package com.idiots.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idiots.authentication.entity.SysAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
public interface SysAccountMapper extends BaseMapper<SysAccount> {

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

}
