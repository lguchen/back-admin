package com.gu.backadmin.config;

import com.gu.backadmin.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author: luo
 * @Date 2023年01月27日 01:18:09
 */


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    // 加自定义拦截器JwtInterceptor，设置拦截规则
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") //拦截所有请求，通过判断token是否合法来决定是否登录
                .excludePathPatterns("/**/export", "/**/import","/**/login","/user/register");//排除这些接口，也就是说，这些接口可以放行

    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
