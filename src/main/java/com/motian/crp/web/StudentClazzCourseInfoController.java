package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudentClazzCourseInfoService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/studentClazzCourseInfo")
public class StudentClazzCourseInfoController {
    private final StudentClazzCourseInfoService service;

    @Autowired
    public StudentClazzCourseInfoController(StudentClazzCourseInfoService service) {
        this.service = service;
    }

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.insert(CrpServiceUtils.getUserId(request), clazzCourseId));
        return model;
    }


    @GetMapping(value = "/listAllByTeacherId")
    public Map<String, Object> listAllByTeacherId(
            @RequestParam(value = "clazzCourseName", required = false, defaultValue = "") String clazzCourseName,
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "") String clazzCourseId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAllByTeacherId(CrpServiceUtils.getUserId(request),
                clazzCourseName, clazzCourseId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "clazzCourseName", required = false, defaultValue = "") String clazzCourseName,
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "") String clazzCourseId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(CrpServiceUtils.getUserId(request),
                clazzCourseId, clazzCourseName, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
