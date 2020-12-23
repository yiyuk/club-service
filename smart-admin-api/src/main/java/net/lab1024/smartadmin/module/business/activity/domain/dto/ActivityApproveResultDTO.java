package net.lab1024.smartadmin.module.business.activity.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * 活动申请
 *
 * @author yiyuzi
 * @date 2020/12/22 23:39
 */

@Data
public class ActivityApproveResultDTO {

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
     * 人数上限
     */
    private Long activityMaximum;

    /**
     * 活动场地
     */
    private String place;

    /**
     * 审批人id
     */
    private Long approveId;

    /**
     * 审批人姓名
     */
    private String approveName;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 活动申请时间 createTime
     */
    private Date createTime;

    /**
     * 活动审核时间 updateTime
     */
    private Date updateTime;
}
