package net.lab1024.smartadmin.module.business.fund.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;

/**
 * @author yiyuzi
 * @date 2020/12/24 15:44
 */

@Data
public class FundUpdateDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("审核者id（校方管理员）")
    private Long approveId;

    @ApiModelPropertyEnum(ApproveTypeEnum.class)
    private Integer approveStatus;

    public FundUpdateDTO() {
    }
}
