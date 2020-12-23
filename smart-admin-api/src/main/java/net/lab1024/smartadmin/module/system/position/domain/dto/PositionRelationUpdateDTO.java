package net.lab1024.smartadmin.module.system.position.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.module.system.position.PositionRelationTypeEnum;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/20 21:07
 */

@Data
public class PositionRelationUpdateDTO{

    @ApiModelProperty("社团ID")
    @NotNull(message = "社团ID 不能为空")
    private Long positionId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID 不能为空")
    private Long employeeId;

    @ApiModelProperty("关联状况")
    @ApiModelPropertyEnum(PositionRelationTypeEnum.class)
    private Integer status;

    @ApiModelProperty("入社审批人ID")
    private Long joinApproverId;

    @ApiModelProperty("退社审批人ID")
    private Long exitApproverId;

    @ApiModelProperty("申请结果")
    private Boolean applyResult;

    @ApiModelProperty("入社时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date joinTime;

    @ApiModelProperty("退社时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date exitTime;

    public PositionRelationUpdateDTO() {
    }

    public PositionRelationUpdateDTO(@NotNull(message = "社团ID 不能为空") Long positionId, @NotNull(message = "用户ID 不能为空") Long employeeId, Integer status, Long joinApproverId, Long exitApproverId, Boolean applyResult, Date joinTime, Date exitTime) {
        this.positionId = positionId;
        this.employeeId = employeeId;
        this.status = status;
        this.joinApproverId = joinApproverId;
        this.exitApproverId = exitApproverId;
        this.applyResult = applyResult;
        this.joinTime = joinTime;
        this.exitTime = exitTime;
    }
}
