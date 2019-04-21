package com.motian.crp.utils;

import java.util.UUID;

import static com.motian.crp.constant.CrpConst.DEFAULT_RANDOM_RANGE_BYTES;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public class CrpServiceUtils {

    public static long generateId() {
        return getRandomNum() + (System.currentTimeMillis() << DEFAULT_RANDOM_RANGE_BYTES);
    }

    private static long getRandomNum() {
        long rangeMaxValue = 1 << DEFAULT_RANDOM_RANGE_BYTES;
        return UUID.randomUUID().hashCode() & (rangeMaxValue - 1) + rangeMaxValue;
    }
}
