package net.lab1024.smartadmin.module.business.activity.constant;

import net.lab1024.smartadmin.common.domain.BaseEnum;

/**
 * @author yiyuzi
 * @date 2020/12/23 21:24
 */

public enum ActivityTimeTypeEnum implements BaseEnum {
    ALL_ACTIVITY(0,"全部活动"),
    NOT_START_ACTIVITY(1,"未开始的活动"),
    ONGOING_ACTIVITY(2,"正在进行的活动"),
    ENDED_ACTIVITY(2,"已结束的活动");

    private Integer value;

    private String desc;

    ActivityTimeTypeEnum(Integer value, String desc) {
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
