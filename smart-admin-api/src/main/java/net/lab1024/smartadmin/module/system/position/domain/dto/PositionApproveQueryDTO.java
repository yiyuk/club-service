package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yiyuzi
 * @date 2020/12/22 14:56
 */

@Data
public class PositionApproveQueryDTO {

    @ApiModelProperty("主键")
    private  Long id;

    @ApiModelProperty("申请状态 YES已通过 NO待审核")
    private Integer status;

    @ApiModelProperty("申请人ID")
    private Long employeeId;

    @ApiModelProperty("审核人ID")
    private Long approverId;

    public PositionApproveQueryDTO() {
    }

    public PositionApproveQueryDTO(Long id, Integer status, Long employeeId, Long approverId) {
        this.id = id;
        this.status = status;
        this.employeeId = employeeId;
        this.approverId = approverId;
    }
}
