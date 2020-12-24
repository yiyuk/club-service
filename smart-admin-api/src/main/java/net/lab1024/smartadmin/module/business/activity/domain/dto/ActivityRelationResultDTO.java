package net.lab1024.smartadmin.module.business.activity.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/24 0:55
 */

@Data
public class ActivityRelationResultDTO {
    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名
     */
    private String activityName;

    /**
     * 社团id
     */
    private Long positionId;

    /**
     * 社团名
     */
    private String positionName;

    /**
     * 参与者ID
     */
    private Long employeeId;

    /**
     * 参与者姓名
     */
    private String employeeName;

    /**
     * 报名状况
     */
    private Integer status;

    /**
     * 参加状况 0/1
     */
    private Integer joinStatus;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 审批人id(社团管理员)
     */
    private Long approveId;

    /**
     * 审批人姓名(社团管理员)
     */
    private String approveName;
}
