package net.lab1024.smartadmin.module.system.position.domain.dto;

import net.lab1024.smartadmin.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty("社团id合集")
    private List<Long> positionIdList;

    @ApiModelProperty("是否为展示列表")
    private Boolean isShow;

    public PositionQueryDTO(){

    }

}
