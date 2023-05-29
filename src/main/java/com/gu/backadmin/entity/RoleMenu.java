package com.gu.backadmin.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @Description
 * @Author: luo
 * @Date 2023年01月29日 20:53:29
 */


@Data
//可以使用 @TableName 表名注解指定当前实体类对应的表名，比如下面 RoleMenu 实体类对应表名为 sys_role_menu
@TableName("sys_role_menu")
public class RoleMenu {
    @TableId("role_id")
    private Integer roleId;
//    @TableId(value = "roleId",type = IdType.INPUT)
    private Integer menuId;
}