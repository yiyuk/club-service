package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yiyuzi
 * @date 2020/12/22 15:03
 */

@Data
public class PositionApproveUpdateDTO {

    @ApiModelProperty("主键")
    private  Long id;

    @ApiModelProperty("审核结果")
    private Boolean approve_result;

    @ApiModelProperty("申请状态 YES已通过 NO待审核")
    private Integer status;

    @ApiModelProperty("审核人ID")
    private Long approverId;

    public PositionApproveUpdateDTO() {
    }

    public PositionApproveUpdateDTO(Long id, Boolean approve_result, Integer status, Long approverId) {
        this.id = id;
        this.approve_result = approve_result;
        this.status = status;
        this.approverId = approverId;
    }
}
