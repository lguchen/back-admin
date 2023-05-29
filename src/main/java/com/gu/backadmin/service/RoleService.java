package com.gu.backadmin.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gu.backadmin.entity.Menu;
import com.gu.backadmin.entity.Role;
import com.gu.backadmin.entity.RoleMenu;
import com.gu.backadmin.mapper.RoleMapper;
import com.gu.backadmin.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
/**
 * @Description
 * @Author: luo
 * @Date 2023年01月28日 10:50:35
 */



@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuService menuService;
    @Transactional
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        //先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        //再把前端传递过来的菜单id数组绑定到角色id上
        for(Integer menuId:menuIds){
            Menu menu=menuService.getById(menuId);
            if(menu.getPid()!=null && !menuIds.contains(menu.getPid())){//二级菜单，并且传过来的menuId数组中没有父级id
                RoleMenu roleMenu=new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuMapper.insert(roleMenu);
            }
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }
    //根据角色id查找相应的菜单id
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
