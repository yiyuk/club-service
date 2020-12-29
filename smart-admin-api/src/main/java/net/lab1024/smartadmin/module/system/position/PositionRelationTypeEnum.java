package net.lab1024.smartadmin.module.system.position;

import net.lab1024.smartadmin.common.domain.BaseEnum;

/**
 * @author yiyuzi
 * @date 2020/12/20 16:27
 */

public enum PositionRelationTypeEnum implements BaseEnum {

    JOIN_WAIT(1, "用户正在等待入团"),

    JOIN_FAIL(2,"用户入团失败"),

    JOIN_SUCCESS(3, "用户入团成功"),

    EXIT_WAIT(4, "用户正在等待退团"),

    EXIT_FAIL(5, "用户退团失败"),

    EXIT_SUCCESS(6, "用户退团成功"),

    ADMIN(7,"用户为社团管理员");



    private Integer value;

    private String desc;

    /**
     * 排序类型：1正序 | 2倒序
     */
    public static final String INFO = "排序类型：1正序 | 2倒序";

    PositionRelationTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举类的值
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
