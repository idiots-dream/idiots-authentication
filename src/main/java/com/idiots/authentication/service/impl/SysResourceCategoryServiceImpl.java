package com.idiots.authentication.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idiots.authentication.mapper.SysResourceCategoryMapper;
import com.idiots.authentication.entity.SysResourceCategory;
import com.idiots.authentication.service.SysResourceCategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台资源分类管理Service实现类
 * @author devil-idiots
 * Date 2022-12-2
 */
@Service
public class SysResourceCategoryServiceImpl extends ServiceImpl<SysResourceCategoryMapper, SysResourceCategory> implements SysResourceCategoryService {

    @Override
    public List<SysResourceCategory> listAll() {
        QueryWrapper<SysResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(SysResourceCategory::getSort);
        return list(wrapper);
    }

    @Override
    public boolean create(SysResourceCategory SysResourceCategory) {
        SysResourceCategory.setCreateTime(LocalDateTime.now());
        return save(SysResourceCategory);
    }
}
