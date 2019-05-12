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

    @GetMapping("/teacher/clazzStudentList")
    private String clazzStudentList() {
        return "/teacher/clazz-course-student-list";
    }

    @GetMapping("/teacher/questionBankList")
    private String questionBankList() {
        return "/teacher/question-bank-list";
    }

    // 管理员相关
    @GetMapping("/admin/index")
    private String toAdminIndex() {
        return "/admin/index";
    }

    @GetMapping("/admin/teacherList")
    private String teacherList() {
        return "/admin/teacher-list";
    }

    @GetMapping("/admin/studentList")
    private String studentList() {
        return "/admin/student-list";
    }

    @GetMapping("/admin/addUser")
    private String addUser() {
        return "/admin/user-add";
    }

    // 学生相关
    @GetMapping("/student/index")
    private String toStudentIndex() {
        return "/student/index";
    }

    @GetMapping("/student/courseList")
    private String courseList() {
        return "/student/course-list";
    }

    @GetMapping("/student/joinCourseList")
    private String joinCourseList() {
        return "/student/join-course-list";
    }
}
