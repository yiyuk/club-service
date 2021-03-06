package net.lab1024.smartadmin.module.business.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.domain.PageParamDTO;
import net.lab1024.smartadmin.module.business.activity.constant.ActivityTimeTypeEnum;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:41
 */

@Data
public class ActivityQueryDTO extends PageParamDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("社团id")
    private Long positionId;

    @ApiModelProperty("组织人id（社团管理员）")
    private Long employeeId;

    @ApiModelPropertyEnum(ActivityTimeTypeEnum.class)
    private Integer timeType;

    @ApiModelProperty("社团id合集")
    private List<Long> positionIdList;

    @ApiModelProperty("活动id合集")
    private List<Long> activityIdList;

    @ApiModelProperty("是否为展示列表")
    private Boolean isShow;

    public ActivityQueryDTO() {
    }
}
