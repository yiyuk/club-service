package net.lab1024.smartadmin.module.business.fund.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;

/**
 * @author yiyuzi
 * @date 2020/12/24 15:43
 */

@Data
public class FundAddDTO {

    @ApiModelProperty("社团id")
    private Long positionId;

    @ApiModelProperty("申请者id（社团管理员）")
    private Long employeeId;

    @ApiModelProperty("申请金额")
    private Double fund;

    @ApiModelProperty("申请理由")
    private String remark;

    public FundAddDTO() {
    }
}
