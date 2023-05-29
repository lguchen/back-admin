package com.gu.backadmin.exception;

import lombok.Getter;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月26日 22:18:16
 */
@Getter
public class ServiceException extends RuntimeException{
    private final String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
}