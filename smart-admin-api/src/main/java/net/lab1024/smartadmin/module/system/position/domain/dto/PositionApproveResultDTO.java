package net.lab1024.smartadmin.module.system.position.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/22 14:55
 */

@Data
public class PositionApproveResultDTO {

    private  Long id;

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
     * 申请人id
     */
    private String actualName;

    /**
     * 审核人ID
     */
    private Long approverId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
