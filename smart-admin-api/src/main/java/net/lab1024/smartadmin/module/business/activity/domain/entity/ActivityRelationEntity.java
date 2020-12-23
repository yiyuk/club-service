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
@TableName("t_position")
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
     * 报名状况
     */
    private Integer status;

    /**
     * 审批时间
     */
    private Date joinTime;

    /**
     * 审批人id(社团管理员)
     */
    private Long approverID;
}
