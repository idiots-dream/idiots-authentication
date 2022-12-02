package com.idiots.authentication.dto;

import com.idiots.authentication.entity.SysMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 * @author devil-idiots
 * Date 2022-12-2
 */
@Getter
@Setter
public class SysMenuNode extends SysMenu {
    private List<SysMenuNode> children;
}
