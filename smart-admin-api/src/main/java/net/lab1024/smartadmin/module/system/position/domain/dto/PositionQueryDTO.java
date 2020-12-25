package net.lab1024.smartadmin.module.system.position.domain.dto;

import net.lab1024.smartadmin.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 社团
 *
 * @author zzr
 */
@Data
public class PositionQueryDTO extends PageParamDTO {

    @ApiModelProperty("社团ID")
    private String id;

    @ApiModelProperty("社团名称")
    private String positionName;

    public PositionQueryDTO(){

    }

}
