package net.lab1024.smartadmin.module.business.message.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.smartadmin.common.anno.ApiModelPropertyEnum;
import net.lab1024.smartadmin.common.constant.JudgeEnum;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:34
 */

@Data
public class MessageAddDTO {

    @ApiModelProperty("发送者id")
    private Long senderId;

    @ApiModelProperty("接收者id")
    private Long receiverId;

    @ApiModelPropertyEnum(JudgeEnum.class)
    private Integer sendStatus;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    public MessageAddDTO() {
    }
}
