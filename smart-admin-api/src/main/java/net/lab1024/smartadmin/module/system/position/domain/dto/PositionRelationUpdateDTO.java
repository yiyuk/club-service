package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @NotNull(message = "关联状况 不能为空")
    private String status;

    @ApiModelProperty("入社审批人ID")
    private Long joinApproverID;

    @ApiModelProperty("退社审批人ID")
    private Long exitApproverID;

    @ApiModelProperty("申请结果")
    private Boolean applyResult;

    @ApiModelProperty("入社时间")
    private Date joinTime;

    @ApiModelProperty("退社时间")
    private Date exitTime;

    public PositionRelationUpdateDTO(@NotNull(message = "社团ID 不能为空") Long positionId, @NotNull(message = "用户ID 不能为空") Long employeeId, @NotNull(message = "关联状况 不能为空") String status, Long joinApproverID, Long exitApproverID, Boolean applyResult, Date joinTime, Date exitTime) {
        this.positionId = positionId;
        this.employeeId = employeeId;
        this.status = status;
        this.joinApproverID = joinApproverID;
        this.exitApproverID = exitApproverID;
        this.applyResult = applyResult;
        this.joinTime = joinTime;
        this.exitTime = exitTime;
    }
}
