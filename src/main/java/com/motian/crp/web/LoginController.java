package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.dao.data.UserData;
import com.motian.crp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Controller
public class LoginController {
    private final UserService service;

    @Autowired
    public LoginController(UserService service) {
        this.service = service;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Map<String, Object> login(
            @RequestParam(value = "accountId") String accountId,
            @RequestParam(value = "token") String token,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("login::accountId={},token={}",accountId,token);
        Map<String, Object> model = Maps.newHashMap();
        model.putAll(service.login(request, accountId,
//                DigestUtils.md5DigestAsHex(token.getBytes())
                token

        ));
        return model;
    }

    @RequestMapping("/")
    public String jumpLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean loginState = (Boolean) request.getSession().getAttribute(RESULT);
        if (loginState != null && loginState) {
                response.sendRedirect(request.getContextPath() + "/home");
        }
        return "login";
    }

    @RequestMapping("/cancellation")
    public String cancellation(HttpServletRequest request, HttpServletResponse response) {
        try {
            Boolean loginState = (Boolean) request.getSession().getAttribute(RESULT);
            if (loginState != null && loginState) {
                request.getSession().setAttribute(RESULT, false);
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/login";
    }

    @RequestMapping("/home")
    public String jumpIndex(HttpServletRequest request, HttpServletResponse response) {
        UserData userData = (UserData) request.getSession().getAttribute(USER_INFO);
        if (userData != null) {
            try {
                switch (userData.getUserType()) {
                    case TEACHER: {
                        response.sendRedirect("/teacher/index");
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
        return "/login";
    }
}
