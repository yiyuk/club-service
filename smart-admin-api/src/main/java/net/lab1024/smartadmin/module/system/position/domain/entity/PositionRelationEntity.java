package net.lab1024.smartadmin.module.system.position.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import net.lab1024.smartadmin.common.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社团-用户关联关系
 *
 * @author zzr
 */
@Data
@TableName("t_position_relation")
public class PositionRelationEntity extends BaseEntity {

    /**
     * 社团ID
     */
    private Long positionId;

    /**
     * 用户ID
     */
    private Long employeeId;

    /**
     * 用户状况
     */
    private String status;

    /**
     * 入社时间
     */
    private Date joinTime;

    /**
     * 入社审批人id
     */
    private Long joinApproverID;

    /**
     * 退社时间
     */
    private Date exitTime;

    /**
     * 退社审批人id
     */
    private Long exitApproverID;

}
