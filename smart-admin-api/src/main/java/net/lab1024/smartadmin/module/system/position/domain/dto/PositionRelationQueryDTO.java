package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.domain.PageParamDTO;
import net.lab1024.smartadmin.module.system.position.PositionRelationTypeEnum;

/**
 * 社团-用户关系
 *
 * @author zzr
 */
@Data
public class PositionRelationQueryDTO extends PageParamDTO {

    @ApiModelProperty("社团ID")
    private Long positionId;

    @ApiModelProperty("用户ID")
    private Long employeeId;

    //@ApiModelProperty("关联状态")
    @ApiModelPropertyEnum(PositionRelationTypeEnum.class)
    private Integer status;

}
