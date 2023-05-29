package com.gu.backadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gu.backadmin.common.Result;
import com.gu.backadmin.entity.Menu;
import com.gu.backadmin.mapper.DictMapper;
import com.gu.backadmin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月27日 01:20:00
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Resource
    private DictMapper dictMapper;
    //增加菜单
    @PostMapping
    public Result save(@RequestBody Menu menu){
        menuService.saveOrUpdate(menu);
        return Result.success();
    }
    //根据id删除菜单
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        menuService.removeById(id);
        return Result.success();
    }
    //批量删除菜单
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        menuService.removeByIds(ids);
        return Result.success();
    }
    //根据id查找菜单
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){

        return Result.success(menuService.getById(id));
    }
    //分页查找
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
    //查询全部菜单
    @GetMapping
    public Result findAll( @RequestParam(defaultValue = "") String name){

        return Result.success(menuService.findMenus(name));
    }
    //查找图标
    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", "icon");
        return Result.success(dictMapper.selectList(null));
    }
    //查询所有菜单id
    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

}