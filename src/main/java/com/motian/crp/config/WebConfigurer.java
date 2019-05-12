package com.motian.crp.config;

import com.google.common.collect.Lists;
import com.motian.crp.intercepors.CrpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private final CrpInterceptor crpInterceptor;

    @Autowired
    public WebConfigurer(CrpInterceptor crpInterceptor) {
        this.crpInterceptor = crpInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //排除不被拦截的资源
        List<String> excludePaths = Lists.newArrayList();
        //layui的静态文件
        excludePaths.add("/layui/**");
        excludePaths.add("/X-admin/**");
        excludePaths.add("/static/**");

        //登录
        excludePaths.add("/");
        excludePaths.add("/login");
        excludePaths.add("/register");
        excludePaths.add("/user/registered");
        excludePaths.add("/welcome");
//        excludePaths.add("/error");

        registry.addInterceptor(crpInterceptor).addPathPatterns("/**").excludePathPatterns(excludePaths);
    }
}
