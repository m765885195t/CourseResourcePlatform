package com.motian.crp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

import static com.motian.crp.constant.CrpConst.ProjectStructure.BASE_PACKAGE_SPACE;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = BASE_PACKAGE_SPACE)
public class CrpApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CrpApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CrpApplication.class, args);
    }
}
