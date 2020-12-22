package net.lab1024.smartadmin.module.system.position.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

/**
 * @author yiyuzi
 * @date 2020/12/22 14:25
 */

@Data
@TableName("t_position_approve")
public class PositionApproveEntity extends BaseEntity {
    /**
     * 社团名称
     */
    private String positionName;

    /**
     * 社团描述
     */
    private String remark;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 申请人ID
     */
    private Long employeeId;

    /**
     * 审核人ID
     */
    private Long approverId;
}
