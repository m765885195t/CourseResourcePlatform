package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.constant.DataType;
import com.motian.crp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;
import static com.motian.crp.constant.CrpConst.StatusField.USER_TYPE;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserService service;

    @ResponseBody
    @PostMapping(value = "/login")
    public Map<String, Object> login(
            @RequestParam(value = "accountId") String accountId,
            @RequestParam(value = "token") String token,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Maps.newHashMap();
        model.putAll(service.login(request, accountId,
//                DigestUtils.md5DigestAsHex(token.getBytes())
                token

        ));
        request.getSession().setAttribute("1", 1);
        return model;
    }

    @RequestMapping("/")
    public String jumpLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            Boolean loginState = (Boolean) request.getSession().getAttribute(RESULT);
            if (loginState != null && loginState) {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/login";
    }

    @GetMapping("/home")
    public String jumpIndex(HttpServletRequest request, HttpServletResponse response) {
        DataType.UserType userType = (DataType.UserType) request.getSession().getAttribute(USER_TYPE);
        if (userType != null) {
            try {
                switch (userType) {
                    case TEACHER: {
                        response.sendRedirect(request.getContextPath() + "/admin/index");
                        break;
                    }
                    case STUDENTS: {
                        break;
                    }
                    case ADMINISTRATOR: {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/home";
    }


}
