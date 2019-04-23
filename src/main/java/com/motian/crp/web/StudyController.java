package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private StudyService service;

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "teacherId") String teacherId,
            @RequestParam(value = "studentId") String studentId,
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            @RequestParam(value = "learningTime") long learningTime) {

        Map<String, Object> model = Maps.newHashMap();
        service.insert(teacherId, studentId, clazzCourseId, learningTime);
        model.put(RESULT, Boolean.TRUE);
        return model;
    }


    @PostMapping(value = "/update")
    public Map<String, Object> update(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "teacherId") String teacherId,
            @RequestParam(value = "studentId") String studentId,
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            @RequestParam(value = "learningTime") long learningTime) throws Exception {

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", service.update(id, teacherId, studentId, clazzCourseId, learningTime));
        return model;
    }

    @PostMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam(value = "id") long id) {

        service.delete(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/listAllByTeacherId")
    public Map<String, Object> listAllByTeacherId(
            @RequestParam(value = "teacherId") String teacherId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("dataList", service.listAllByTeacherId(teacherId, pageNumber, pageSize));
        return model;
    }

    @PostMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("dataList", service.listAll(pageNumber, pageSize));
        return model;
    }
}
