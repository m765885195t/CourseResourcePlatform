package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.dao.data.UserData;
import com.motian.crp.service.ClazzCourseService;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
@RequestMapping("/clazzCourse")
public class ClazzCourseController {

    private final ClazzCourseService service;

    @Autowired
    public ClazzCourseController(ClazzCourseService service) {
        this.service = service;
    }

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "teacherId") String teacherId,
            @RequestParam(value = "clazzCourseName") String clazzCourseName) {

        Map<String, Object> model = Maps.newHashMap();
        service.insert(teacherId, clazzCourseName);
        model.put(RESULT, Boolean.TRUE);
        return model;
    }


    @PostMapping(value = "/update")
    public Map<String, Object> update(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "clazzCourseName", required = false, defaultValue = "") String clazzCourseName) throws Exception {
        Map<String, Object> model = Maps.newHashMap();
        service.update(id, clazzCourseName);
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam(value = "id") long id) {
        service.delete(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/getByClazzCourseId")
    public Map<String, Object> getByClazzCourseId(
            @RequestParam(value = "clazzCourseId") long clazzCourseId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.getByClazzCourseId(clazzCourseId));
        return model;
    }

    @GetMapping(value = "/listAllByTeacherId")
    public Map<String, Object> listAllByTeacherId(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        String teacherId = StringUtils.EMPTY;

        UserData userData = (UserData) request.getSession().getAttribute(USER_INFO);
        if (userData != null) {
            switch (userData.getUserType()) {
                case TEACHER: {
                    teacherId = userData.getAccountId();
                    break;
                }
            }
        }
        Map<String, Object> model = Maps.newHashMap();

        model.put("data", service.listAllByTeacherId(teacherId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
