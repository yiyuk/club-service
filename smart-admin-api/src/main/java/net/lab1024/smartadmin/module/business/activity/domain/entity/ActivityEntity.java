package net.lab1024.smartadmin.module.business.activity.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

import java.util.Date;

/**
 * 活动表
 *
 * @author yiyuzi
 * @date 2020/12/22 23:08
 */

@Data
@TableName("t_activity")
public class ActivityEntity extends BaseEntity {

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
     * 组织人id
     */
    private Long employeeId;

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
