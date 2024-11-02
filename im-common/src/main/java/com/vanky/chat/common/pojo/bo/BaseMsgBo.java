package com.vanky.chat.common.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author vanky
 * @create 2024/11/2 21:11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseMsgBo {

    /**
     * 消息 id
     */
    private Long id;

    /**
     * 全局唯一id
     */
    private Long uniqueId;

    /**
     * 发送方
     */
    private Long fromUserId;

    /**
     * 接收方
     */
    private Long toUserId;

    /**
     * 发送时间
     */
    private Date createTime;

    /**
     * 聊天类型
     */
    private int chatType;

    /**
     * 消息内容
     */
    private byte[] content;

    /**
     * 消息类型
     */
    private int msgType;

    /**
     * 状态
     */
    private int status;

}
