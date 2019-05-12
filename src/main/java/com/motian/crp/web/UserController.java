package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.constant.DataType;
import com.motian.crp.dao.data.UserData;
import com.motian.crp.service.UserService;
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
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/registered")
    public Map<String, Object> registered(
            @RequestParam(value = "accountId") String accountId,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "userType") int userType) {
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.registered(accountId,nickname,
//                DigestUtils.md5DigestAsHex(token.getBytes())
                token
                , userType));
        return model;
    }

    @PostMapping(value = "/addUser")
    public Map<String, Object> addUser(
            @RequestParam(value = "accountId") String accountId,
            @RequestParam(value = "userType") int userType,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "token", required = false, defaultValue = "") String token,
            @RequestParam(value = "gender", required = false, defaultValue = "-1") int gender,
            @RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
            @RequestParam(value = "cellphoneNumber") String cellphoneNumber,
            @RequestParam(value = "email") String email) throws Exception {
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.addUser(accountId, userType, token, nickname,
                gender, birthday, cellphoneNumber, email));
        return model;
    }

    @PostMapping(value = "/unsubscribe")
    public Map<String, Object> unsubscribe(@RequestParam(value = "id") long id) {
        service.unsubscribe(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, Boolean.TRUE);
        return model;
    }

    @PostMapping(value = "/modify")
    public Map<String, Object> modify(
            @RequestParam(value = "accountId") String accountId,
            @RequestParam(value = "oldToken", required = false, defaultValue = "") String oldToken,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "newToken", required = false, defaultValue = "") String newToken,
            @RequestParam(value = "gender", required = false, defaultValue = "-1") int gender,
            @RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
            @RequestParam(value = "cellphoneNumber") String cellphoneNumber,
            @RequestParam(value = "email") String email,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = Maps.newHashMap();
        log.info("modify::accountId={}", accountId);
        model.put(RESULT, service.update(accountId, oldToken, newToken, nickname,
                gender, birthday, cellphoneNumber, email));
        UserData userData = service.getByAccountId(accountId);
        request.getSession().setAttribute(USER_INFO, userData);
        return model;
    }

    @PostMapping(value = "/getByAccountId")
    public Map<String, Object> getByAccountId(@RequestParam(value = "accountId") String accountId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", service.getByAccountId(accountId));
        return model;
    }

    @PostMapping(value = "/resetToken")
    public Map<String, Object> resetToken(@RequestParam(value = "accountId") String accountId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", service.resetToken(accountId));
        return model;
    }

    @GetMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "accountId", required = false, defaultValue = "") String accountId,
            @RequestParam(value = "nickName", required = false, defaultValue = "") String nickName,
            @RequestParam(value = "userType") int userType,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize) {
        log.info("listAll::userType={}", userType);
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", service.listAll(DataType.UserType.getType(userType).get(),
                accountId, nickName, pageNumber, pageSize));
        return CrpWebUtils.Model(model);
    }
}
