package com.motian.crp.utils;

import java.util.UUID;

import static com.motian.crp.constant.CrpConst.DEFAULT_RANDOM_RANGE_BYTES;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public class CrpServiceUtils {

    public static long generateId() {
        return getRandomNum(DEFAULT_RANDOM_RANGE_BYTES) + (System.currentTimeMillis() << DEFAULT_RANDOM_RANGE_BYTES);
    }

    public static long getRandomNum(int bit) {
        long rangeMaxValue = 1 << DEFAULT_RANDOM_RANGE_BYTES;
        return UUID.randomUUID().hashCode() & (rangeMaxValue - 1) + rangeMaxValue;
    }
}
