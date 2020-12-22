package net.lab1024.smartadmin.module.system.position.domain.dto;

import lombok.Data;
import net.lab1024.smartadmin.common.domain.PageParamDTO;

import java.util.Date;

/**
 * 社团-用户关联关系
 *
 * @author zzr
 */
@Data
public class PositionRelationResultDTO extends PageParamDTO {

    private Long id;

    /**
     * 社团ID
     */
    private Long positionId;

    /**
     * 用户ID
     */
    private Long employeeId;

    /**
     * 社团名称
     */
    private String positionName;

    /**
     * 用户名称
     */
    private String actualName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 关联状况
     */
    private Integer status;

    /**
     * 入社时间
     */
    private Date joinTime;

    /**
     * 入社审批人id
     */
    private Long joinApproverId;

    /**
     * 退社时间
     */
    private Date exitTime;

    /**
     * 退社审批人id
     */
    private Long exitApproverId;

}
