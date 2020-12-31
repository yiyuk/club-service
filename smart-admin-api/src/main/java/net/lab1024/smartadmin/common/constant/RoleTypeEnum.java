package net.lab1024.smartadmin.common.constant;

import net.lab1024.smartadmin.common.domain.BaseEnum;

/**
 * @author yiyuzi
 * @date 2020/12/30 10:04
 */

public enum  RoleTypeEnum implements BaseEnum {

    SUPER_ADMIN(1, "超级管理员"),

    SCHOOL_ADMIN(50, "校方管理员"),

    CLUB_ADMIN(51, "社团管理员"),

    USER(52, "用户");

    private Integer value;

    private String desc;

    RoleTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取定义枚举value值
     *
     * @return Integer
     */
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 获取枚举类的说明
     *
     * @return String
     */
    @Override
    public String getDesc() {
        return desc;
    }

}
