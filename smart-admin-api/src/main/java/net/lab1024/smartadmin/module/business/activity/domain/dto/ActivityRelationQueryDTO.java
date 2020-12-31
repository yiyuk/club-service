package net.lab1024.smartadmin.module.business.activity.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageParamDTO;
import net.lab1024.smartadmin.module.business.activity.constant.ActivityTimeTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * @author yiyuzi
 * @date 2020/12/24 0:55
 */

@Data
public class ActivityRelationQueryDTO extends PageParamDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("参与者ID")
    private Long employeeId;

    @ApiModelProperty("社团ID")
    private Long positionId;

    @ApiModelProperty("社团id合集")
    private List<Long> positionIdList;

    @ApiModelProperty("是否为展示列表")
    private Boolean isShow;

    @ApiModelPropertyEnum(ActivityTimeTypeEnum.class)
    private Integer timeType;

    /**
     * 报名状况
     */
    @ApiModelPropertyEnum(ApproveTypeEnum.class)
    private Integer status;

    /**
     * 参加状况 0/1
     */
    @ApiModelPropertyEnum(JudgeEnum.class)
    private Integer joinStatus;

    @ApiModelProperty("审批人id(社团管理员)")
    private Long approveId;

    public ActivityRelationQueryDTO() {
    }
}
