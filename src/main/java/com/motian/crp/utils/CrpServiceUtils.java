package com.motian.crp.utils;

import com.motian.crp.dao.data.UserData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.motian.crp.constant.CrpConst.DEFAULT_RANDOM_RANGE_BYTES;
import static com.motian.crp.constant.CrpConst.StatusField.USER_INFO;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
@Slf4j
public class CrpServiceUtils {
    public static final Map<Integer, Integer> ValueMap = new HashMap<Integer, Integer>() {
        {
            put(0, 50);
            put(1, 100);
            put(2, 150);
            put(3, 200);
        }
    };

    public static long generateId() {
        return getRandomNum(DEFAULT_RANDOM_RANGE_BYTES) + (System.currentTimeMillis() << DEFAULT_RANDOM_RANGE_BYTES);
    }

    public static long getRandomNum(int bit) {
        long rangeMaxValue = 1 << DEFAULT_RANDOM_RANGE_BYTES;
        return UUID.randomUUID().hashCode() & (rangeMaxValue - 1) + rangeMaxValue;
    }

    public static String getUserId(HttpServletRequest request) {
        String userID = StringUtils.EMPTY;
        UserData userData = (UserData) request.getSession().getAttribute(USER_INFO);
        if (userData != null) {
            userID = userData.getAccountId();
        }
        log.info("getUserId::userID={}", userID);

        return userID;
    }

}
