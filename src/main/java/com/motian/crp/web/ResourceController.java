package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.constant.DataType;
import com.motian.crp.service.ResourceService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
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
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService service;

    @Autowired
    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload")
    public Map<String, Object> upload(
            @RequestParam(value = "resourceName") String resourceName,
            @RequestParam(value = "committer") String committer,
            @RequestParam(value = "resourceType") int resourceType,
            @RequestParam(value = "resourceUri") String resourceUri,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        service.insert(resourceName, committer,
                DataType.ResourceType.getType(resourceType).get(), resourceUri);
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/download")
    public Map<String, Object> download(
            @RequestParam(value = "id") long id,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = Maps.newHashMap();
        service.download(id);
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
}
