package com.coral.mini.program.common.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 联系人对象
 * @Author: hbHao
 * @Date: 2020/11/17 10:56
 */
@Data
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
}
