package com.gu.backadmin.entity;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月26日 22:02:09
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String token;
    //把当前登录用户的角色以及他的菜单项带出来
//    private String avatar;
    private String role;
    private List<Menu> menus;
}