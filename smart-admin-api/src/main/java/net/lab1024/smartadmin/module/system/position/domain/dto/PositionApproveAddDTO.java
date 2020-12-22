package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yiyuzi
 * @date 2020/12/22 15:40
 */

@Data
public class PositionApproveAddDTO {

    @ApiModelProperty("社团名称")
    private String positionName;

    @ApiModelProperty("社团描述")
    private String remark;

    @ApiModelProperty("申请人ID")
    private Long employeeId;

    public PositionApproveAddDTO() {
    }

    public PositionApproveAddDTO(String positionName, String remark, Long employeeId) {
        this.positionName = positionName;
        this.remark = remark;
        this.employeeId = employeeId;
    }
}
