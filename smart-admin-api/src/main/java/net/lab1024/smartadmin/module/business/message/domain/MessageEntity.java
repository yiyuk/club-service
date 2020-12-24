package net.lab1024.smartadmin.module.business.message.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.smartadmin.common.domain.BaseEntity;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:33
 */

@Data
@TableName("t_message")
public class MessageEntity extends BaseEntity {

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 接收者id
     */
    private Long receiverId;

    /**
     * 是否发送
     */
    private Integer sendStatus;

    /**
     * 是否已读
     */
    private Integer receiveStatus;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;
}
