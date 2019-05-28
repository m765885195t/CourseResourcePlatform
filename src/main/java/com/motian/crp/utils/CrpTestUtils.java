package com.motian.crp.utils;

import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.dao.manager.ClazzChapterManager;
import com.motian.crp.dao.manager.ClazzCourseManager;
import com.motian.crp.service.ClazzChapterResourcesService;
import com.motian.crp.service.ClazzChapterService;
import com.motian.crp.service.ClazzCourseService;
import com.motian.crp.service.QuestionBankService;
import com.motian.crp.service.ResourceService;
import com.motian.crp.service.StudentClazzCourseInfoService;
import com.motian.crp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.motian.crp.constant.DataType.UserType.ADMINISTRATOR;
import static com.motian.crp.constant.DataType.UserType.STUDENTS;
import static com.motian.crp.constant.DataType.UserType.TEACHER;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Component
public class CrpTestUtils {
    @Autowired
    private UserService userService;
    @Autowired
    private ClazzCourseService clazzCourseService;
    @Autowired
    private ClazzCourseManager clazzCourseManager;
    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private ClazzChapterManager clazzChapterManager;
    @Autowired
    private StudentClazzCourseInfoService studentClazzCourseInfoService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ClazzChapterResourcesService clazzChapterResourcesService;

    @PostConstruct
    public void init() throws Exception {
        // 创建管理员信息
//        initAdmin("admin");
        initAdmin("1");
        // 创建老师与课程信息
//        initTeacher("765885195@qq.com");
        initTeacher("2");
        initStudent("3");
        // 创建问题数据
//        initQuestion("765885195@qq.com");
    }


    private void initAdmin(String value) {
        userService.registered(value, value, value, ADMINISTRATOR.code);
    }


    private void initStudent(String value) {
        userService.registered(value, value, value, STUDENTS.code);
    }

    private void initTeacher(String value) throws Exception {
        // 老师
        userService.registered(value, value, value, TEACHER.code);

        // 课程
        clazzCourseService.insert(value, "JAVA", 50);
        ClazzCourseData clazzCourseData1 = clazzCourseService
                .getByTeacherIdAndClazzCourseName(value, "JAVA");

        // 章节
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                1, "第一章:总体框架");
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                2, "第二章:ArrayList详细介绍");
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                3, "第三章:Map架构");


        // 课程
        clazzCourseService.insert(value, "C++", 200);
        ClazzCourseData clazzCourseData2 = clazzCourseService
                .getByTeacherIdAndClazzCourseName(value, "C++");

        // 章节
        clazzChapterService.insert(clazzCourseData2.getClazzCourseId(),
                1, "第一章:认识C");
        clazzChapterService.insert(clazzCourseData2.getClazzCourseId(),
                2, "第二章:学习C");
        clazzChapterService.insert(clazzCourseData2.getClazzCourseId(),
                3, "第三章:练习C");

    }

}
