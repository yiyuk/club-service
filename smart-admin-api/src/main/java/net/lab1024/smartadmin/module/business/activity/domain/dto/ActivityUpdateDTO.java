package net.lab1024.smartadmin.module.business.activity.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/22 23:42
 */

@Data
public class ActivityUpdateDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("活动名称")
    @NotBlank(message = "活动名称不能为空")
    private String activityName;

    @ApiModelProperty("活动简介")
    @NotBlank(message = "活动简介不能为空")
    private String activityRemark;

    @ApiModelProperty("活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;

    @ApiModelProperty("人数上限")
    private Long activityMaximum;

    @ApiModelProperty("活动场地")
    private String place;
}
