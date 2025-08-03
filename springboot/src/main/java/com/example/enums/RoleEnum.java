package com.example.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN", "管理员"),
    TEACHER("TEACHER", "教师"),
    USER("USER", "学生用户");

    private final String code;
    private final String label;

    RoleEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static RoleEnum fromCode(String code) {
        for (RoleEnum value : RoleEnum.values()) {
            if (value.getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效角色标识: " + code);
    }
}
