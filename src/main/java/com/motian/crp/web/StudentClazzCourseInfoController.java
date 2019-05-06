package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudentClazzCourseInfoService;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
@RequestMapping("/studentClazzCourseInfo")
public class StudentClazzCourseInfoController {
    private final StudentClazzCourseInfoService service;

    @Autowired
    public StudentClazzCourseInfoController(StudentClazzCourseInfoService service) {
        this.service = service;
    }


    @GetMapping(value = "/listAllByStudentId")
    public Map<String, Object> listAllByStudentId(
            @RequestParam(value = "studentId") String studentId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAllByStudentId(studentId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }

    @GetMapping(value = "/listAllByClazzCourseId")
    public Map<String, Object> listAllByClazzCourseId(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAllByClazzCourseId(clazzCourseId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
