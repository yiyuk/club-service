package net.lab1024.smartadmin.module.system.position;

import net.lab1024.smartadmin.common.domain.BaseEnum;

/**
 * @author yiyuzi
 * @date 2020/12/22 15:21
 */

public enum PositionApproveTypeEnum  implements BaseEnum {

    /**
     * 等待审核
     */
    WAIT(1, "等待审核"),

    /**
     * 不通过
     */
    FAIL(2, "不通过"),

    /**
     * 通过
     */
    SUCCESS(3, "通过");


    private Integer value;

    private String desc;

    /**
     * 排序类型：1正序 | 2倒序
     */
    public static final String INFO = "排序类型：1正序 | 2倒序";

    PositionApproveTypeEnum(Integer value, String desc) {
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