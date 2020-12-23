package net.lab1024.smartadmin.module.business.activity.constant;

import net.lab1024.smartadmin.common.domain.BaseEnum;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:28
 */

public enum ActivityApproveStatusTypeEnum implements BaseEnum {

    ;

    private Integer value;

    private String desc;

    ActivityApproveStatusTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
