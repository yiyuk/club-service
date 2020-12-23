package net.lab1024.smartadmin.module.business.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 审核活动申请
 *
 * @author yiyuzi
 * @date 2020/12/22 23:41
 */

@Data
public class ActivityApproveUpdateDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("审批人id（校方管理员）")
    private Long approveId;

    @ApiModelPropertyEnum(ApproveTypeEnum.class)
    private Integer status;

    public ActivityApproveUpdateDTO() {
    }
}
