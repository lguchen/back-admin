package com.gu.backadmin.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gu.backadmin.entity.Menu;
import com.gu.backadmin.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月28日 10:50:20
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        List<Menu> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes=list.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        //找出一级菜单为null的二级菜单放到Children中
        for(Menu menu:parentNodes){
            menu.setChildren(list.stream().filter(m->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }

}