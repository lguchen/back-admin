package com.gu.backadmin.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gu.backadmin.common.Constants;
import com.gu.backadmin.entity.Menu;
import com.gu.backadmin.entity.User;
import com.gu.backadmin.entity.UserDTO;
import com.gu.backadmin.exception.ServiceException;
import com.gu.backadmin.mapper.RoleMapper;
import com.gu.backadmin.mapper.RoleMenuMapper;
import com.gu.backadmin.mapper.UserMapper;
import com.gu.backadmin.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月22日 02:34:13
 */

@Service
public class UserService extends ServiceImpl<UserMapper, User>{
    private UserMapper userMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuService menuService;

    public Boolean saveUser(User user) {
        return saveOrUpdate(user);
    }


    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");//这里假设查询了多于1条记录，就让他报系统错误
        }
        if (one != null) {  //以下是登录判断业务
            BeanUtil.copyProperties(one, userDTO, true);
            //设置token
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword().toString());
            userDTO.setToken(token);
            String role = one.getRole();//查询出用户的角色标识，比如ROLE_ADMIN
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    /**
     * 获取当前用户的菜单列表
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        //根据角色标识获取角色Id
        Integer roleId = roleMapper.selectByflag(roleFlag);
        //当前角色Id的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出系统所有菜单
        List<Menu> menus = menuService.findMenus("");
        //筛选当前用户菜单
        List<Menu> roleMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
    //通过用户名查找
    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return getOne(wrapper);
    }
//    用户注册
    public void register(User user) {
        save(user);
    }

}