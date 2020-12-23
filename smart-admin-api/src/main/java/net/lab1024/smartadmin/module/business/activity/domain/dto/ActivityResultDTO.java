package net.lab1024.smartadmin.module.business.activity.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:07
 */

@Data
public class ActivityResultDTO {

    private Long id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动简介
     */
    private String activityRemark;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date stopTime;

    /**
     * 社团id
     */
    private Long positionId;

    /**
     * 社团名
     */
    private String positionName;

    /**
     * 组织人id
     */
    private Long employeeId;

    /**
     * 组织人姓名
     */
    private String employeeName;

    /**
     * 当前活动人数
     */
    private Long activityNumber;

    /**
     * 人数上限
     */
    private Long activityMaximum;

    /**
     * 活动场地
     */
    private String place;
}
