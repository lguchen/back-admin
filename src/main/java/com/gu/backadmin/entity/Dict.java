package com.gu.backadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月28日 10:54:53
 */
@Data
@TableName("sys_dict")
public class Dict {
    @TableId
    private String name;
    private String value;
    private String type;
}