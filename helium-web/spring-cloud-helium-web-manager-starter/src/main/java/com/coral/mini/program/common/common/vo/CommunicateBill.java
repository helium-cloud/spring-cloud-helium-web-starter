package com.coral.mini.program.common.common.vo;


/**
 * @Description: 通信展示
 * @Author: hbHao
 * @Date: 2020/6/17 15:12
 */
public class CommunicateBill {

    private String id;

    private int cardType;

    private String fromUser;

    private String toUser;

    private int  communicateMode;

    private int  communicateType;

    private int  communicateAddress;

    private String operationTime;

    private String duration;//通话时长


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public int getCommunicateMode() {
        return communicateMode;
    }

    public void setCommunicateMode(int communicateMode) {
        this.communicateMode = communicateMode;
    }

    public int getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(int communicateType) {
        this.communicateType = communicateType;
    }

    public int getCommunicateAddress() {
        return communicateAddress;
    }

    public void setCommunicateAddress(int communicateAddress) {
        this.communicateAddress = communicateAddress;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
