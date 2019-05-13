package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.ClazzChapterResourcesService;
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
@RequestMapping("/clazzChapterResources")
public class ClazzChapterResourcesController {
    private final ClazzChapterResourcesService service;

    @Autowired
    public ClazzChapterResourcesController(ClazzChapterResourcesService service) {
        this.service = service;
    }

    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            @RequestParam(value = "clazzChapterId") long clazzChapterId,
            @RequestParam(value = "resourceLibraryId") long resourceLibraryId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.insert(clazzChapterId, resourceLibraryId));
        return model;
    }


    @PostMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam(value = "id") long id) {
        service.delete(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/getByClazzChapterResourcesId")
    public Map<String, Object> getByClazzChapterResourcesId(
            @RequestParam(value = "id") long id) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.getById(id));
        return model;
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "clazzChapterName", required = false, defaultValue = "") String clazzChapterName,
            @RequestParam(value = "resourceLibraryName", required = false, defaultValue = "") String resourceLibraryName,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(clazzChapterName, resourceLibraryName, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
