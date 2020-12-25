package net.lab1024.smartadmin.module.business.activity.constant;

import net.lab1024.smartadmin.common.constant.ResponseCodeConst;

/**
 * @author yiyuzi
 * @date 2020/12/23 16:27
 */

public class ActivityResponseCodeConst extends ResponseCodeConst {

    public static final ActivityResponseCodeConst TIME_ERROR = new ActivityResponseCodeConst(14000, "活动开始时间不能晚于结束时间和当前时间");

    public static final ActivityResponseCodeConst MAX_NUMBER_ERROR = new ActivityResponseCodeConst(14001, "活动人数上限不能小于0或小于当前活动人数");

    public static final ActivityResponseCodeConst NUMBER_LIMIT_ERROR = new ActivityResponseCodeConst(14002, "当前活动已到达人数上限");

    /**
     * 参与活动申请
     */
    public static final ActivityResponseCodeConst RESUBMIT_ERROR = new ActivityResponseCodeConst(14003, "当前活动正在等待审核或已成功报名");

    public static final ActivityResponseCodeConst NOT_RUN_ERROR = new ActivityResponseCodeConst(14005, "当前活动未开始或已结束");

    public static final ActivityResponseCodeConst RE_APPROVE_ERROR = new ActivityResponseCodeConst(14006, "当前活动已审核");

    public static final ActivityResponseCodeConst NOT_EXIST_ERROR = new ActivityResponseCodeConst(14007, "活动不存在");

    protected ActivityResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}
