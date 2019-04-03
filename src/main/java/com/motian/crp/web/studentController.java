package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: gongzhanjing
 * @Email: gongzhanjing@xiyoulinux.org
 */
@RestController
@RequestMapping("/test")
public class studentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/addUser")
    public Map<String, Object> addUser() {
        Map<String, Object> model = Maps.newHashMap();
        model.put("addUser", studentService.addUserInfo("2"));
        return model;
    }
}
