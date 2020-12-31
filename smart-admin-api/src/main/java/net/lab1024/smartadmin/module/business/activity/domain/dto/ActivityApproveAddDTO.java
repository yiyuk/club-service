package net.lab1024.smartadmin.module.business.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 添加活动申请
 *
 * @author yiyuzi
 * @date 2020/12/22 23:40
 */

@Data
public class ActivityApproveAddDTO {

    @ApiModelProperty("活动名称")
    @NotBlank(message = "活动名称不能为空")
    private String activityName;

    @ApiModelProperty("活动简介")
    private String activityRemark;

    @ApiModelProperty("活动开始时间")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;

    @ApiModelProperty("社团id")
    @NotNull(message = "社团id不能为空")
    private Long positionId;

    @ApiModelProperty("组织人id（社团管理员）")
    private Long employeeId;

    @ApiModelProperty("人数上限")
    @NotNull(message = "人数上限不能为空")
    //只能输入数字
    //@Pattern(regexp = "^[0-9]*$")
    private Long activityMaximum;

    @ApiModelProperty("活动地点")
    @NotBlank(message = "活动地点不能为空")
    private String place;

    /**
     * 申请状态
     */
    //private Integer status;

    /**
     * 活动申请时间 createTime
     */

    /**
     * 活动审核时间 updateTime
     */

    public ActivityApproveAddDTO() {
    }
}
