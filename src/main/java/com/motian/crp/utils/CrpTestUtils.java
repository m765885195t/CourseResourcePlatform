package com.motian.crp.utils;

import com.motian.crp.dao.data.ClazzCourseData;
import com.motian.crp.service.ClazzChapterService;
import com.motian.crp.service.ClazzCourseService;
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

    @PostConstruct
    public void init() {
        // 用户
        userService.registered("1", "1", ADMINISTRATOR.code);
        userService.registered("2", "2", TEACHER.code);
        userService.registered("3", "3", STUDENTS.code);

        // 课程
        clazzCourseService.insert("2", "测试课程1", 10);
        clazzCourseService.insert("2", "测试课程2", 10);
        ClazzCourseData clazzCourseData1 = clazzCourseService
                .getByTeacherIdAndClazzCourseName("2", "测试课程1");
        // 章节
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                1, clazzCourseData1.getClazzCourseName() + "章节1");
        clazzChapterService.insert(clazzCourseData1.getClazzCourseId(),
                2, clazzCourseData1.getClazzCourseName() + "章节2");

        ClazzCourseData clazzCourseData2 = clazzCourseService
                .getByTeacherIdAndClazzCourseName("2", "测试课程2");
        clazzChapterService.insert(clazzCourseData2.getClazzCourseId(),
                1, clazzCourseData2.getClazzCourseName() + "章节1");
        clazzChapterService.insert(clazzCourseData2.getClazzCourseId(),
                2, clazzCourseData2.getClazzCourseName() + "章节2");
    }
}
