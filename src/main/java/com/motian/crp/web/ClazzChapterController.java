package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.ClazzChapterService;
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
@RequestMapping("/clazzChapter")
public class ClazzChapterController {
    private final ClazzChapterService service;

    @Autowired
    public ClazzChapterController(ClazzChapterService service) {
        this.service = service;
    }

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "clazzCourseId") long clazzCourseId,
            @RequestParam(value = "clazzCourseChapterOrder") int order,
            @RequestParam(value = "clazzChapterName") String clazzChapterName,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.insert(clazzCourseId, order, clazzChapterName));
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

    @PostMapping(value = "/updateQuestionId")
    public Map<String, Object> updateQuestionId(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "questionId", required = false, defaultValue = "-1")
                    String questionId) throws Exception {
        Map<String, Object> model = Maps.newHashMap();
        service.updateQuestionId(id, Long.parseLong(questionId));
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

    @PostMapping(value = "/getByClazzChapterId")
    public Map<String, Object> getByClazzChapterId(
            @RequestParam(value = "clazzChapterId") long clazzChapterId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.getById(clazzChapterId));
        return model;
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "clazzCourseName", required = false, defaultValue = "") String clazzCourseName,
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "") String clazzCourseId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("listAll::clazzCourseId={}", clazzCourseId);
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(CrpServiceUtils.getUserId(request),
                clazzCourseId, clazzCourseName, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }

    @GetMapping(value = "/selectClazzCourseChapter")
    public Map<Long, String> selectClazzCourseChapter(
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "-1") long clazzCourseId,
            HttpServletRequest request, HttpServletResponse response) {
        return service.selectClazzCourseChapter(CrpServiceUtils.getUserId(request), clazzCourseId);
    }
}
