package com.motian.crp.web;

import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.service.ClazzChapterService;
import com.motian.crp.service.ResourceService;
import com.motian.crp.service.ResourceUserInfoService;
import com.motian.crp.service.StudyService;
import com.motian.crp.utils.CrpServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Controller
public class JumpPageController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceUserInfoService service;
    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private StudyService studyService;

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

    @GetMapping("/teacher/resourceList")
    private String resourceList() {
        return "/teacher/resource-list";
    }

    @GetMapping("/teacher/upload")
    private String resourceUpload() {
        return "/teacher/resource-add";
    }

    @GetMapping("/teacher/clazzChapterResourceList")
    private String clazzChapterResourceList() {
        return "/teacher/clazz-chapter-resource-list";
    }

    @GetMapping("/teacher/clazzCourseUseList")
    private String clazzCourseUseList() {
        return "/teacher/clazz-course-use-list";
    }

    @GetMapping("/teacher/clazzCourseResourceUseList")
    private String clazzCourseResourceUseList() {
        return "/teacher/clazz-course-resource-use-list";
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

    @GetMapping("/student/studyList")
    private String studyList(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("clazzCourseId", clazzCourseId);
        return "/student/chapter-study-list";
    }

    @RequestMapping(value = "/watchVideo/{path}")
    public String watchVideo(
            @PathVariable(name = "path") long path,
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResourceLibraryData resourceLibraryData = resourceService.get(path);

        model.addAttribute("path", "/videos/" +
                resourceLibraryData.getResourceRealName());

        ClazzChapterData clazzChapterData = clazzChapterService.getById(clazzChapterId);
        service.update(resourceLibraryData.getCommitter(), clazzChapterData.getClazzCourseId(),
                clazzChapterData.getId(), resourceLibraryData.getId());

        studyService.update(CrpServiceUtils.getUserId(request),
                resourceLibraryData.getCommitter(), clazzChapterData.getClazzCourseId(),
                clazzChapterData.getId(), resourceLibraryData.getId());

        return "/student/video";
    }
}
