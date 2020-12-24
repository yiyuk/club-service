package net.lab1024.smartadmin.module.business.activity.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author yiyuzi
 * @date 2020/12/24 0:52
 */

@Data
public class ActivityRelationAddDTO {
    /**
     * 活动ID
     */
    @ApiModelProperty("活动ID")
    private Long activityId;

    public ActivityRelationAddDTO() {
    }
}
