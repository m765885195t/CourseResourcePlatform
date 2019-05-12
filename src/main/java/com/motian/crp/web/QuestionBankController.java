package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.QuestionBankService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
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
@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {

    private final QuestionBankService service;

    @Autowired
    public QuestionBankController(QuestionBankService service) {
        this.service = service;
    }

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "teacherId") String teacherId,
            @RequestParam(value = "studentId") String content) {
        Map<String, Object> model = Maps.newHashMap();
        service.insert(teacherId, content);
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

    @RequestMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "questionBankId", required = false, defaultValue = "-1") long questionBankId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(CrpServiceUtils.getUserId(request), questionBankId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
