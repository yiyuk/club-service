package net.lab1024.smartadmin.module.business.activity.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.ApproveTypeEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/24 0:54
 */

@Data
public class ActivityRelationUpdateDTO {

    @ApiModelProperty("主键")
    private Long id;

    /**
     * 参加状况
     */
    @ApiModelPropertyEnum(JudgeEnum.class)
    private Integer joinStatus;

    /**
     * 审批结果 更新status approveTime approverID
     */
    @ApiModelPropertyEnum(ApproveTypeEnum.class)
    private Integer status;

    public ActivityRelationUpdateDTO() {
    }
}
