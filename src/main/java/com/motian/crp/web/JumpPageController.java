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

    // 老师相关
    @GetMapping("/teacher/index")
    public String toTeacherIndex() {
        return "/teacher/index";
    }

    @GetMapping("/teacher/clazzCourseAdd")
    private String clazzCourseAdd() {
        return "/teacher/clazz-course-add";
    }

    @GetMapping("/teacher/clazzCourseList")
    private String clazzCourseList() {
        return "/teacher/clazz-course-list";
    }

    @GetMapping("/teacher/clazzChapterList")
    private String clazzChapterList() {
        return "/teacher/clazz-course-chapter-list";
    }
}
