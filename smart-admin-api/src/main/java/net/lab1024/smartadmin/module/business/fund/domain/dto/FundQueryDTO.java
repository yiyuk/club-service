package net.lab1024.smartadmin.module.business.fund.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.PageParamDTO;

/**
 * @author yiyuzi
 * @date 2020/12/24 15:44
 */

@Data
public class FundQueryDTO extends PageParamDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("社团id")
    private Long positionId;

    @ApiModelProperty("申请者id（社团管理员）")
    private Long employeeId;

    @ApiModelProperty("审核者id（校方管理员）")
    private Long approveId;

    @ApiModelProperty("审核状态")
    private Integer approveStatus;

    public FundQueryDTO() {
    }
}
