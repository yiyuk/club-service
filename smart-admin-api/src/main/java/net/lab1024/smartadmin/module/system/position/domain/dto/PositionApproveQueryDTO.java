package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;

/**
 * @author yiyuzi
 * @date 2020/12/22 14:56
 */

@Data
public class PositionApproveQueryDTO {

    @ApiModelProperty("主键")
    private  Long id;

    @ApiModelPropertyEnum(ApproveTypeEnum.class)
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
