package com.motian.crp.config;

import com.motian.crp.intercepors.CrpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addInterceptor(crpInterceptor)
                .addPathPatterns("/**");
    }
}
