package net.lab1024.smartadmin.module.business.activity.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/23 9:20
 */

@Data
@TableName("t_activity_relation")
public class ActivityRelationEntity extends BaseEntity {
    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 参与者ID
     */
    private Long employeeId;

    /**
     * 社团id
     */
    private Long positionId;

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
}
