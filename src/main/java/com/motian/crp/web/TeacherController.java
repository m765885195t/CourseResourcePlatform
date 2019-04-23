package com.motian.crp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @GetMapping("/index")
    public String toAdminIndex() {
        return "/teacher/index";
    }

    @GetMapping("/clazz-course-add")
    public String toCourse() {
        return "/teacher/clazz-course-add";
    }

    @GetMapping("/clazz-course-list")
    public String toCourses() {
        return "/teacher/clazz-course-list";
    }

    @GetMapping("/clazzCourse/{courseId}")
    public String toCourInfo(@PathVariable int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "/teacher/course-info";
    }
}
