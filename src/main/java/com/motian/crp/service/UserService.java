package com.motian.crp.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.motian.crp.constant.CrpConst.DefaultValue.TOKEN;
import static com.motian.crp.constant.CrpConst.StatusField.RESULT;
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;
import static com.motian.crp.constant.CrpConst.StatusField.USER_TYPE;
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
    public boolean registered(String accountId, String nickname, String token, int userType) {
        if (manager.getByAccountId(accountId).isPresent()) {
            return false;
        }

        create(accountId, nickname, token, DataType.UserType.getType(userType).orElse(STUDENTS));
        return true;
    }


    public boolean addUser(String accountId, int userType, String token, String nickname,
                           int gender, String birthday, String cellphoneNumber, String email) throws Exception {
        if (manager.getByAccountId(accountId).isPresent()) {
            return false;
        }
        create(accountId, nickname, token, DataType.UserType.getType(userType).orElse(STUDENTS));
        update(accountId, token, token, nickname, gender, birthday, cellphoneNumber, email);
        return true;
    }

    private void create(String accountId, String nickname, String token, DataType.UserType userType) {
        manager.save(new UserData()
                .setAccountId(accountId)
                .setNickname(nickname)
                .setToken(token)
                .setUserType(userType));
    }

    public void unsubscribe(long id) {
        manager.findById(id).ifPresent((o) -> manager.delete(o));
    }

    public UserData getByAccountId(String accountId) {
        return manager.getByAccountId(accountId).orElse(null);
    }

    public List<UserData> listAll(DataType.UserType userType, String accountId, String nickName, int pageNumber, int pageSize) {
        Set<UserData> userDataSet = Sets.newHashSet();
        userDataSet.addAll(listByAccountId(userType, accountId, pageNumber, pageSize));
        userDataSet.addAll(listByNickName(userType, nickName, pageNumber, pageSize));
        return new ArrayList<>(userDataSet)
                .stream()
                .sorted(Comparator.comparing(UserData::getId))
                .collect(Collectors.toList());
    }

    private List<UserData> listByAccountId(DataType.UserType userType, String accountId, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> o.getUserType() == userType)
                .filter(o -> StringUtils.isBlank(accountId) || accountId.equals(o.getAccountId()))
                .collect(Collectors.toList());
    }

    private List<UserData> listByNickName(DataType.UserType userType, String nickName, int pageNumber, int pageSize) {
        return manager.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent()
                .stream()
                .filter(o -> o.getUserType() == userType)
                .filter(o -> StringUtils.isBlank(nickName) || nickName.equals(o.getNickname()))
                .collect(Collectors.toList());
    }


    public Map<String, Object> login(HttpServletRequest request, String accountId, String token) {

        Map<String, Object> model = Maps.newHashMap();
        Optional<UserData> optionalUserData = Optional.ofNullable(getByAccountId(accountId));
        if (optionalUserData.isPresent() && optionalUserData.get().getToken().equals(token)) {
            request.getSession().setAttribute(USER_INFO, optionalUserData.get());
            request.getSession().setAttribute(RESULT, true);
            model.put(USER_TYPE, optionalUserData.get().getUserType().code);
            model.put(RESULT, true);
        } else {
            request.getSession().setAttribute(RESULT, false);
            model.put(RESULT, false);
        }
        return model;
    }

    public boolean update(String accountId, String oldToken, String newToken,
                          String nickname, int gender, String birthday,
                          String cellphoneNumber, String email) throws Exception {
        Optional<UserData> data = manager.getByAccountId(accountId);
        if (!data.isPresent()) {
            throw new Exception("The user does not exist. accountId=" + accountId);
        }

        DataType.GenderType.getType(gender).ifPresent(
                genderType -> data.get().setGender(genderType));

        if (!StringUtils.isBlank(nickname)) {
            data.get().setNickname(nickname);
        }

        if (!StringUtils.isBlank(birthday)) {
            data.get().setBirthday(birthday);
        }

        if (!StringUtils.isBlank(cellphoneNumber)) {
            data.get().setCellphoneNumber(cellphoneNumber);
        }

        if (!StringUtils.isBlank(email)) {
            data.get().setEmail(email);
        }
        manager.save(data.get());
        if (!StringUtils.isBlank(oldToken)) {
            if (data.get().getToken().equals(oldToken)) {
                data.get().setToken(newToken);
                manager.save(data.get());
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean resetToken(String accountId) {
        Optional<UserData> userData = manager.getByAccountId(accountId);

        if (!userData.isPresent()) {
            return false;
        }
        userData.get().setToken(TOKEN);
        manager.save(userData.get());
        return true;
    }

}
