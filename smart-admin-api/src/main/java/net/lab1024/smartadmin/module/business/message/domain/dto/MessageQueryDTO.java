package net.lab1024.smartadmin.module.business.message.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;
import net.lab1024.smartadmin.common.domain.PageParamDTO;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:35
 */

@Data
public class MessageQueryDTO extends PageParamDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("发送者id")
    private Long senderId;

    @ApiModelProperty("接收者id")
    private Long receiverId;

    @ApiModelPropertyEnum(JudgeEnum.class)
    private Integer sendStatus;

    @ApiModelPropertyEnum(JudgeEnum.class)
    private Integer receiveStatus;

    @ApiModelProperty("消息标题")
    private String title;

    public MessageQueryDTO() {
    }
}
