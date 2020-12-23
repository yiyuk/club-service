package net.lab1024.smartadmin.module.business.activity.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

import java.util.Date;

/**
 * 活动审核表
 *
 * @author yiyuzi
 * @date 2020/12/22 23:14
 */

@Data
@TableName("t_activity_approve")
public class ActivityApproveEntity extends BaseEntity {
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
     * 组织人id（社团管理员）
     */
    private Long employeeId;

    /**
     * 审批人id（校方管理员）
     */
    private Long approveId;

    /**
     * 申请状态
     */
    private Integer status;

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

    /**
     * 活动申请时间 createTime
     */

    /**
     * 活动审核时间 updateTime
     */

}
