package com.motian.crp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Controller
public class JumpPageController {
    @GetMapping("/register")
    private String register() {
        return "/register";
    }

    @GetMapping("/welcome")
    private String welcome() {
        return "/welcome";
    }

    @GetMapping("/teacher/clazzCourseInfo")
    private String clazzCourseList() {
        return "/teacher/clazz-course-info";
    }

}
