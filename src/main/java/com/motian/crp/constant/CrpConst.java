package com.motian.crp.constant;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public interface CrpConst {
    int DEFAULT_RANDOM_RANGE_BYTES = 10;
    int NUMBER_RANDOM_RANGE_BYTES = 6;
    String SEPARATOR = "=====";

    interface ProjectStructure {
        String BASE_PACKAGE_SPACE = "com.motian.crp";
        String INTERFACE_DOCUMENTATION = "com.motian.crp.web";
        String DEFAULT_FILE_PATH = "G:\\tmp\\";
    }

    interface StatusField {
        String RESULT = "result";
        String USER_TYPE = "userType";
        String USER_NICKNAME = "userNickname";
        String USER_INFO = "userInfo";
    }

    interface DefaultValue {
        String NICKNAME = "motian";
        String TOKEN = "password";
    }
}
