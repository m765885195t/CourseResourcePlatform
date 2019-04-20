package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@RestController
public class studentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/addUser")
    public Map<String, Object> addUser(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", studentService.addUserInfo("aaa"));
        return model;
    }


    @GetMapping(value = "/getUser")
    public Map<String, Object> getUser(
            @RequestParam(value = "studuntId") Long studuntId,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("user", studentService.getById(studuntId));
        return model;
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("users", studentService.listAll());
        return model;
    }
}
