package net.lab1024.smartadmin.module.business.message.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yiyuzi
 * @date 2020/12/24 17:34
 */

@Data
public class MessageResultDTO {

    private Long id;

    /**
     * 发送者id
     */
    private Long senderId;

    private String senderName;

    /**
     * 接收者id
     */
    private Long receiverId;

    private String receiverName;

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

    private Date updateTime;

    private Date createTime;
}
