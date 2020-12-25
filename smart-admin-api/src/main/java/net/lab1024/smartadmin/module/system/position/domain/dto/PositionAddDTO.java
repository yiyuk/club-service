package net.lab1024.smartadmin.module.system.position.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 社团
 *
 * @author zzr
 */
@Data
public class PositionAddDTO {

    /**
     * 社团名称
     */
    @ApiModelProperty("社团名称")
    @NotBlank(message = "社团名称不能为空")
    private String positionName;

    /**
     * 社团描述
     */
    @ApiModelProperty("社团描述")
    private String remark;

    public PositionAddDTO(){

    }
}
