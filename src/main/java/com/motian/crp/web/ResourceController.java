package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.ResourceService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService service;

    @Autowired
    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload")
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        service.upload(request, response);
        model.put(RESULT, Boolean.TRUE);
        return CrpWebUtils.Model(model);
    }


    @RequestMapping(value = "/download/{path}/{clazzChapterId}")
    public void download(@PathVariable(name = "path") long path,
                         @PathVariable(name = "clazzChapterId") long clazzChapterId,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.download(request, response, path, clazzChapterId);
    }


    @PostMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam(value = "id") long id) {
        service.delete(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/update")
    public Map<String, Object> update(@RequestParam(value = "id") long resourceId,
                                      @RequestParam(value = "resourceName") String resourceName,
                                      HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.update(CrpServiceUtils.getUserId(request),
                resourceId, resourceName));
        return model;
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "resourceId", required = false, defaultValue = "") String resourceId,
            @RequestParam(value = "resourceName", required = false, defaultValue = "") String resourceName,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(CrpServiceUtils.getUserId(request),
                resourceId, resourceName, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }

    @GetMapping(value = "/selectVideoResource")
    public Map<Long, String> selectVideoResource(HttpServletRequest request, HttpServletResponse response) {
        return service.selectVideoResource();

    }

    @GetMapping(value = "/selectDocResource")
    public Map<Long, String> selectDocResource(HttpServletRequest request, HttpServletResponse response) {
        return service.selectDocResource();
    }
}
