package com.motian.crp.service;

import com.google.common.collect.Maps;
import com.motian.crp.constant.DataType;
import com.motian.crp.dao.data.UserData;
import com.motian.crp.dao.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.motian.crp.constant.CrpConst.StatusField.RESULT;
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;
import static com.motian.crp.constant.DataType.UserType.STUDENTS;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserManager manager;

    @Transactional
    public boolean registered(String accountId, String token, int userType) {
        if (manager.getByAccountId(accountId).isPresent()) {
            return false;
        }
        create(accountId, token, DataType.UserType.getType(userType).orElse(STUDENTS));
        return true;
    }

    private void create(String accountId, String token, DataType.UserType userType) {
        manager.save(new UserData()
                .setAccountId(accountId)
                .setToken(token)
                .setUserType(userType));
    }

    @Transactional
    public UserData update(long id, String token, String nickname,
                           int gender, String birthday, String cellphoneNumber)
            throws Exception {
        Optional<UserData> data = manager.findById(id);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. id=" + id);
        }
        if (!StringUtils.isBlank(token)) {
            data.get().setToken(token);
        }
        if (!StringUtils.isBlank(nickname)) {
            data.get().setNickname(nickname);
        }

        DataType.GenderType.getType(gender).ifPresent(
                genderType -> data.get().setGender(genderType));

        if (!StringUtils.isBlank(cellphoneNumber)) {
            data.get().setCellphoneNumber(cellphoneNumber);
        }
        if (!StringUtils.isBlank(birthday)) {
            data.get().setBirthday(birthday);
        }
        return manager.save(data.get());
    }

    public void unsubscribe(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }

    public UserData getByAccountId(String accountId) {
        return manager.getByAccountId(accountId).orElse(null);
    }

    public List<UserData> listAll(int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    public Map<String, Object> login(HttpServletRequest request, String accountId, String token) {

        Map<String, Object> model = Maps.newHashMap();
        Optional<UserData> optionalUserData = Optional.ofNullable(getByAccountId(accountId));
        if (optionalUserData.isPresent() && optionalUserData.get().getToken().equals(token)) {
            request.getSession().setAttribute(USER_INFO, optionalUserData.get());
            request.getSession().setAttribute(RESULT, true);
            model.put(RESULT, true);
        } else {
            request.getSession().setAttribute(RESULT, false);
            model.put(RESULT, false);
        }
        return model;
    }

}
