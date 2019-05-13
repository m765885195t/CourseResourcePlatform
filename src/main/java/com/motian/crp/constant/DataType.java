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

        WOMAN(3),
        ;
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
        UNKNOWN(0),

        ADMINISTRATOR(1),

        TEACHER(2),

        STUDENTS(3),
        ;

        public final int code;

        public static Optional<UserType> getType(int code) {
            UserType type = null;
            for (UserType t : UserType.values()) {
                if (t.code == code) {
                    type = t;
                }
            }
//            if (type == ADMINISTRATOR) {
//                type = STUDENTS;
//            }
            return Optional.ofNullable(type);
        }
    }

    @AllArgsConstructor
    public enum ResourceType {
        VIDEO(1),

        AUDIO(2),

        LINK(3),

        DOCUMENT(4),

        PICTURE(5),
        ;
        public final int code;

        public static Optional<ResourceType> getType(int code) {
            ResourceType type = null;
            for (ResourceType t : ResourceType.values()) {
                if (t.code == code) {
                    type = t;
                }
            }
            return Optional.ofNullable(type);
        }
    }
}
