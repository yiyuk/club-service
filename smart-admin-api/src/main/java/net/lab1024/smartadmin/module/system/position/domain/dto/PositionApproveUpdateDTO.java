package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;

/**
 * @author yiyuzi
 * @date 2020/12/22 15:03
 */

@Data
public class PositionApproveUpdateDTO {

    @ApiModelProperty("主键")
    private  Long id;

    @ApiModelPropertyEnum(ApproveTypeEnum.class)
    private Integer status;

    @ApiModelProperty("审核人ID")
    private Long approverId;

    public PositionApproveUpdateDTO() {
    }

    public PositionApproveUpdateDTO(Long id, Integer status, Long approverId) {
        this.id = id;
        this.status = status;
        this.approverId = approverId;
    }
}
