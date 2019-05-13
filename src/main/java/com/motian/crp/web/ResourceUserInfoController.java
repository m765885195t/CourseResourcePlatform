package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.ResourceUserInfoService;
import com.motian.crp.utils.CrpServiceUtils;
import com.motian.crp.utils.CrpWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/resourceUserInfo")
public class ResourceUserInfoController {
    private final ResourceUserInfoService service;

    @Autowired
    public ResourceUserInfoController(ResourceUserInfoService service) {
        this.service = service;
    }
    @RequestMapping(value = "/listByTeacherId")
    public Map<String, Object> listByTeacherId(
            @RequestParam(value = "clazzCourseId", required = false, defaultValue = "-1") long clazzCourseId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listByTeacherId(CrpServiceUtils.getUserId(request), clazzCourseId, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
