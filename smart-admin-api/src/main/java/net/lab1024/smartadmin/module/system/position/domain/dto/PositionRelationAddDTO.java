package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
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

    @ApiModelPropertyEnum(PositionRelationTypeEnum.class)
    @NotNull(message = "关联状况 不能为空")
    private Integer status;

    public PositionRelationAddDTO() {
    }

    public PositionRelationAddDTO(List<Long> positionIdList, Long employeeId) {
        this.positionIdList = positionIdList;
        this.employeeId = employeeId;
        this.status = PositionRelationTypeEnum.JOIN_SUCCESS.getValue();
    }

    public PositionRelationAddDTO(List<Long> positionIdList, Long employeeId, Integer status) {
        this.positionIdList = positionIdList;
        this.employeeId = employeeId;
        this.status = status;
    }

    public PositionRelationAddDTO(@NotNull(message = "社团ID 不能为空") Long positionId, @NotNull(message = "用户ID 不能为空") Long employeeId, @NotNull(message = "关联状况 不能为空") Integer status) {
        this.positionId = positionId;
        this.employeeId = employeeId;
        this.status = status;
    }
}
