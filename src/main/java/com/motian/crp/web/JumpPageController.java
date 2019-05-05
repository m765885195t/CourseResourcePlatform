package com.motian.crp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    private String clazzCourseInfo() {
        return "/teacher/clazz-course-info";
    }

    @GetMapping("/teacher/clazzCourseList")
    private String clazzCourseList() {
        return "/teacher/clazz-course-list";
    }

    @GetMapping("/teacher/index")
    public String toAdminIndex() {
        return "/teacher/index";
    }

    @GetMapping("/teacher/clazz-course-list")
    public String toCourses() {
        return "/teacher/clazz-course-list";
    }

    @GetMapping("/teacher/clazzCourse/{courseId}")
    public String toCourInfo(@PathVariable int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "/teacher/course-info";
    }

}
