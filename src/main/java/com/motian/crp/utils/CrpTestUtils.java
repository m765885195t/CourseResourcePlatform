package com.motian.crp.utils;

import com.motian.crp.constant.DataType;
import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.service.ClazzChapterService;
import com.motian.crp.service.ClazzCourseService;
import com.motian.crp.service.QuestionBankService;
import com.motian.crp.service.ResourceService;
import com.motian.crp.service.StudentClazzCourseInfoService;
import com.motian.crp.service.UserService;
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
@Component
public class CrpTestUtils {
    @Autowired
    private UserService userService;
    @Autowired
    private ClazzCourseService clazzCourseService;
    @Autowired
    private ClazzChapterService clazzChapterService;
    @Autowired
    private StudentClazzCourseInfoService studentClazzCourseInfoService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private ResourceService resourceService;

    @PostConstruct
    public void init() throws Exception {
        // 创建管理员信息
        initAdmin("1");
        // 创建老师与课程信息
        initTeacher("2");
        // 创建学生与课程信息
        initStudent("3");
        // 创建问题数据

        initQuestion("2");
        // 创建资源
        initResource("2");
    }

    private void initResource(String value) {
        resourceService.insert("资源1", value,
                DataType.ResourceType.VIDEO, "/disk/1");
        resourceService.insert("资源2", value,
                DataType.ResourceType.AUDIO, "/disk/2");
        resourceService.insert("资源3", value,
                DataType.ResourceType.LINK, "/disk/3");
        resourceService.insert("资源4", value,
                DataType.ResourceType.DOCUMENT, "/disk/4");
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
        // 学生1
        userService.registered(value + "1", value + "1", value + "1", STUDENTS.code);
        // 学生1
        userService.registered(value + "2", value + "2", value + "2", STUDENTS.code);

        // 课程
        clazzCourseService.insert(value, "测试课程" + value, 10);
        ClazzCourseData clazzCourseData1 = clazzCourseService
                .getByTeacherIdAndClazzCourseName(value, "测试课程" + value);
        // 章节
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                1, clazzCourseData1.getClazzCourseName() + "章节1");
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                2, clazzCourseData1.getClazzCourseName() + "章节2");

        // 参加课程
        studentClazzCourseInfoService.insert(value + "1", clazzCourseData1.getClazzCourseId());
        studentClazzCourseInfoService.insert(value + "2", clazzCourseData1.getClazzCourseId());
    }

    private void initQuestion(String teacherId) {
        questionBankService.insert(teacherId, "content1");
        questionBankService.insert(teacherId, "content2");
    }
}
