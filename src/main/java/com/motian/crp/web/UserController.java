package com.motian.crp.web;

import com.google.common.collect.Maps;
import com.motian.crp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
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
            @RequestParam(value = "token") String token,
            @RequestParam(value = "userType") int userType) {
        Map<String, Object> model = Maps.newHashMap();
        model.put(RESULT, service.registered(accountId,
//                DigestUtils.md5DigestAsHex(token.getBytes())
                token
                , userType));
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
            @RequestParam(value = "id") long id,
            @RequestParam(value = "password", required = false, defaultValue = "") String password,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "gender", required = false, defaultValue = "-1") int gender,
            @RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
            @RequestParam(value = "cellphoneNumber", required = false, defaultValue = "") String cellphoneNumber) throws Exception {

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", service.update(id, password, nickname,
                gender, birthday, cellphoneNumber));
        return model;
    }

    @PostMapping(value = "/getByAccountId")
    public Map<String, Object> getByAccountId(@RequestParam(value = "accountId") String accountId) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("user", service.getByAccountId(accountId));
        return model;
    }

    @PostMapping(value = "/listAll")
    public Map<String, Object> listAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize) {

        Map<String, Object> model = Maps.newHashMap();
        model.put("dataList", service.listAll(pageNumber, pageSize));
        return model;
    }
}
