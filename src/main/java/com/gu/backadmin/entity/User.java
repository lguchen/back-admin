package com.gu.backadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月22日 01:29:23
 */
@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO) //写入数据库的id错误时添加
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private String address;
    @TableField(value="created_time")//这样处理的主要目的是java对带有下划线的字段不识别，所以改为驼峰形式
    private Date createdTime;//如果需要年月日格式的可以使用Date类型，如果需要具体到时分秒就使用String类型

    private String avatar;
    private String role;
}