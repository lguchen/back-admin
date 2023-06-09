package com.gu.backadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月28日 10:45:45
 */
@Data
@TableName("sys_menu")
public class Menu {

    //可以使用 @TableId 注解（标注在主键上）和 @TableField 注解（标注在其他成员属性上）来指定对应的字段名
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private String description;

    //在数据表中没有children这个字段，这个在做菜单的时候会用到，所以使用exist=false忽略
    @TableField(exist = false)
    private List<Menu> children;

    private Integer pid;
    @TableField(value="page_path")//这样处理的主要目的是java对带有下划线的字段不识别，所以改为驼峰形式
    private String pagePath;
}