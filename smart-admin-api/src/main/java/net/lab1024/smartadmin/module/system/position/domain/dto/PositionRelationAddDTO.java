package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.module.system.position.PositionRelationTypeEnum;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 社团-用户关系
 *
 * @author zzr
 */
@Data
public class PositionRelationAddDTO {

    @ApiModelProperty("社团ID")
    @NotNull(message = "社团ID 不能为空")
    private List<Long> positionIdList;

    @ApiModelProperty("社团ID")
    @NotNull(message = "社团ID 不能为空")
    private Long positionId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID 不能为空")
    private Long employeeId;

    @ApiModelProperty("关联状况")
    @NotNull(message = "关联状况 不能为空")
    private String status;

    public PositionRelationAddDTO() {
    }

    public PositionRelationAddDTO(List<Long> positionIdList, Long employeeId) {
        this.positionIdList = positionIdList;
        this.employeeId = employeeId;
        this.status = PositionRelationTypeEnum.JOIN_WAIT.toString();
    }

    public PositionRelationAddDTO(@NotNull(message = "社团ID 不能为空") Long positionId, @NotNull(message = "用户ID 不能为空") Long employeeId, @NotNull(message = "关联状况 不能为空") String status) {
        this.positionId = positionId;
        this.employeeId = employeeId;
        this.status = status;
    }
}
