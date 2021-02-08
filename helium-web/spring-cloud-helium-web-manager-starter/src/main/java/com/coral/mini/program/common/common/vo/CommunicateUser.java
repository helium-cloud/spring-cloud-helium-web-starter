package com.coral.mini.program.common.common.vo;


import java.util.List;

/**
 * @Description: 联系人对象
 * @Author: hbHao
 * @Date: 2020/11/17 10:56
 */
public class CommunicateUser {

    private String number;

    private int cardType;

    private List<CommunicateBill> communicateBillList;

    private List<CommunicateMessage> communicateMessageList;

    public String getNumber() {
        if(communicateBillList!=null){
           return communicateBillList.get(0).getFromUser();
        }
        if(communicateMessageList!=null){
            return communicateMessageList.get(0).getFromUser();
        }
        return number;
    }

    public int getCardType() {
        if(communicateBillList!=null){
            return communicateBillList.get(0).getCardType();
        }
        if(communicateMessageList!=null){
            return communicateMessageList.get(0).getCardType();
        }
        return cardType;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public List<CommunicateBill> getCommunicateBillList() {
        return communicateBillList;
    }

    public void setCommunicateBillList(List<CommunicateBill> communicateBillList) {
        this.communicateBillList = communicateBillList;
    }

    public List<CommunicateMessage> getCommunicateMessageList() {
        return communicateMessageList;
    }

    public void setCommunicateMessageList(List<CommunicateMessage> communicateMessageList) {
        this.communicateMessageList = communicateMessageList;
    }
}
