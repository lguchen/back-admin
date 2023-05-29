package com.gu.backadmin.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @Description
 * @Author: luo
 * @Date 2023年01月29日 20:50:40
 */


import java.sql.Date;

@Data
//可以使用 @TableName 表名注解指定当前实体类对应的表名，比如下面 Role 实体类对应表名为 sys_role
@TableName("sys_role")
public class Role {
    //可以使用 @TableId 注解（标注在主键上）和 @TableField 注解（标注在其他成员属性上）来指定对应的字段名
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String flag;
}
