package com.coral.mini.program.common.common.vo;

import lombok.Data;

/**
 * @Description: 消息展示
 * @Author: hbHao
 * @Date: 2020/6/17 15:12
 */
@Data
public class CommunicateMessage {

    private String id;

    private int cardType;

    private String fromUser;

    private String toUser;

    private int  communicateMode;

    private int  communicateType;

    private int  communicateAddress;

    private String operationTime;

}
