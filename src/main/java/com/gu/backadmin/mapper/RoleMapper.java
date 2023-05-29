package com.gu.backadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gu.backadmin.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author guchen
 */


public interface RoleMapper extends BaseMapper<Role> {
    //根据角色唯一标识flag查找角色id
    @Select("select id from sys_role where flag=#{flag}")
    Integer selectByflag(@Param("flag") String role);
}
