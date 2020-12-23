package net.lab1024.smartadmin.common.constant;

import net.lab1024.smartadmin.common.domain.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author yiyuzi
 * @date 2020/12/23 9:05
 */

public enum ApproveTypeEnum implements BaseEnum {

    WAIT(1, "等待审核"),

    FAIL(2, "不通过"),

    SUCCESS(3, "通过");

    private Integer value;
    private String desc;

    ApproveTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
    @Override
    public String getDesc() {
        return desc;
    }

    public static JudgeEnum valueOf(Integer status) {
        JudgeEnum[] values = JudgeEnum.values();
        Optional<JudgeEnum> first = Arrays.stream(values).filter(e -> e.getValue().equals(status)).findFirst();
        return !first.isPresent() ? null : first.get();
    }

    public static boolean isExist(Integer status) {
        JudgeEnum judgeEnum = valueOf(status);
        return judgeEnum != null;
    }
}
