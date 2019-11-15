package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudentClazzChapterInfoService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
@RequestMapping("/studentClazzChapterInfo")
public class StudentClazzChapterInfoController {

    @Autowired
    private StudentClazzChapterInfoService service;

    @PostMapping(value = "/submit")
    public Map<String, Object> submit(
            @RequestParam(value = "text") String[] text,
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.save(CrpServiceUtils.getUserId(request),
                clazzChapterId, text));
        return model;
    }

    @PostMapping(value = "/updateResults")
    public Map<String, Object> updateResults(
            @RequestParam(value = "results") int results,
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        String studentId = (String) request.getSession().getAttribute("clazzCourseIdStudentId");

        model.put(RESULT, service.updateResults(studentId, results, clazzChapterId));
        return model;
    }


    @RequestMapping(value = "/listByTeacherId")
    public Map<String, Object> listByTeacherId(
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "-1") long clazzCourseId,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listByTeacherId(CrpServiceUtils.getUserId(request), clazzCourseId));
        return CrpWebUtils.Model(model);
    }
}
