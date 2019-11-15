package com.motian.crp.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.motian.crp.dao.data.ClazzChapterData;
import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.data.QuestionBankData;
import com.motian.crp.dao.data.ResourceLibraryData;
import com.motian.crp.dao.data.StudentClazzChapterInfoData;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.service.ClazzChapterService;
import com.motian.crp.service.ClazzCourseService;
import com.motian.crp.service.QuestionBankService;
import com.motian.crp.service.ResourceService;
import com.motian.crp.service.ResourceUserInfoService;
import com.motian.crp.service.StudentClazzChapterInfoService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.motian.crp.constant.CrpConst.SEPARATOR;

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
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private StudentClazzChapterInfoService studentClazzChapterInfoService;
    @Autowired
    private ClazzCourseService clazzCourseService;
    @Autowired
    private ClazzCourseManager clazzCourseManager;

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

    @GetMapping("/teacher/questionBankAdd")
    private String questionBankAdd() {
        return "/teacher/question-bank-add";
    }

    @GetMapping("/teacher/resourceList")
    private String resourceList() {
        return "/teacher/resource-list";
    }

    @GetMapping("/teacher/mountResource")
    private String mountResource() {
        return "/teacher/mount-resource";
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

    @GetMapping("/teacher/clazzCourseStudyInfoList")
    private String clazzCourseStudyInfoList(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            @RequestParam(value = "studentId") String studentId,
            HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("clazzCourseIdStudyInfoId", clazzCourseId);
        request.getSession().setAttribute("clazzCourseIdStudentId", studentId);

        return "/teacher/clazz-course-study-info-list";
    }

    @GetMapping("/teacher/clazzCourseResourceUseList")
    private String clazzCourseResourceUseList() {
        return "/teacher/clazz-course-resource-use-list";
    }

    @RequestMapping(value = "/questionAdd")
    public String questionAdd(
            String questionName, String[] timu,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        questionBankService.insert(CrpServiceUtils.getUserId(request), Arrays.asList(timu), questionName);
        return questionBankList();
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

    @RequestMapping(value = "/question/{path}")
    public String question(
            @PathVariable(name = "path") long path,
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {


        ClazzChapterData clazzChapterData = clazzChapterService.getById(clazzChapterId);

        QuestionBankData questionBankData = questionBankService.get(clazzChapterData.getQuestionId());

        String[] exams = questionBankData.getContent().split(SEPARATOR);
        model.addAttribute("courseId", clazzChapterData.getId());
        model.addAttribute("exams", exams);
        model.addAttribute("courseId2", clazzChapterData.getClazzCourseId());

        return "/student/question";
    }

    @RequestMapping(value = "/teacher/student")
    public String submitTest(
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        String studentId = (String) request.getSession().getAttribute("clazzCourseIdStudentId");

        Optional<StudentClazzChapterInfoData> studentClazzChapterInfoData =
                studentClazzChapterInfoService.get(studentId, clazzChapterId);
        List<Map<String, String>> ans = Lists.newArrayList();
        log.info("studentId={}", studentId);
        log.info("isPresent={}", studentClazzChapterInfoData.isPresent());

        if (studentClazzChapterInfoData.isPresent()) {
            String[] key = studentClazzChapterInfoData.get().getContent().split(SEPARATOR);
            String[] value = studentClazzChapterInfoData.get().getAnswer().split(SEPARATOR);
            log.info("key={}", key);
            log.info("value={}", value);
            for (int i = 0; i < key.length; i++) {
                Map<String, String> tmp = Maps.newHashMap();
                tmp.put("key", key[i]);
                tmp.put("value", value[i]);
                ans.add(tmp);
            }
        }
        model.addAttribute("ans", ans);
        model.addAttribute("clazzChapterId", clazzChapterId);
        log.info("ans={}", ans);
        return "/teacher/teacher-student-question-test";
    }

    @GetMapping("/teacher/chapterResultsList")
    private String chapterResultsList() {
        return "/teacher/chapter-results-list";
    }

    @RequestMapping(value = "/teacher/question")
    public String questionList(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        ClazzCourseData clazzCourseData = clazzCourseManager.getOne(clazzCourseId);
        if (clazzCourseData.getQuestion() == null || clazzCourseData.getQuestion().isEmpty()) {
            model.addAttribute("exams", new String[]{});
        } else {
            String[] exams = clazzCourseData.getQuestion().split(SEPARATOR);
            model.addAttribute("exams", exams);
        }

        model.addAttribute("courseId", clazzCourseData.getId());
        return "/teacher/question";
    }

    @RequestMapping(value = "/student/teacher")
    public String getQuestionList(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            Model model,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        ClazzCourseData clazzCourseData = clazzCourseManager.getOne(clazzCourseId);
        List<Map<String, String>> ans = Lists.newArrayList();

        if (clazzCourseData.getQuestion() != null && !clazzCourseData.getQuestion().isEmpty()) {
            String[] key = clazzCourseData.getQuestion().split(SEPARATOR);
            String[] value = clazzCourseData.getAnswer().split(SEPARATOR);
            for (int i = 0; i < key.length; i++) {
                Map<String, String> tmp = Maps.newHashMap();
                tmp.put("key", key[i]);

                tmp.put("value", value[i]);
                ans.add(tmp);
            }
        }

        model.addAttribute("ans", ans);
        model.addAttribute("clazzChapterId", clazzCourseId);
        return "/student/teacher-student-question-list";
    }
}
