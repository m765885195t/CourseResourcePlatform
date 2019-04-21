package com.motian.crp.constant;

import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public class DataType {
    @AllArgsConstructor
    public enum GenderType {
        UNKNOWN(1),

        MAN(2),

        WOMAN(3),;
        public final int code;

        public static Optional<GenderType> getType(int code) {
            GenderType type = null;
            for (GenderType t : GenderType.values()) {
                if (t.code == code) {
                    type = t;
                }
            }
            return Optional.ofNullable(type);
        }
    }

    @AllArgsConstructor
    public enum UserType {
        ADMINISTRATOR(1),

        TEACHER(2),

        STUDENTS(3),;

        public final int code;

        public static Optional<UserType> getType(int code) {
            UserType type = null;
            for (UserType t : UserType.values()) {
                if (t.code == code) {
                    type = t;
                }
            }
            if (type == ADMINISTRATOR) {
                type = STUDENTS;
            }
            return Optional.ofNullable(type);
        }

    }
}
