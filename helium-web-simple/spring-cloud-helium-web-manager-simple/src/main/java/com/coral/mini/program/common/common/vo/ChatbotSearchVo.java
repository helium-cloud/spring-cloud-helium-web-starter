package com.coral.mini.program.common.common.vo;

import lombok.Data;


@Data
public class ChatbotSearchVo {

    private String ecNo;
    //关键字
    private String selectCondition;

    private String accessNo;

    private String chatbotId ;

    //与分页有关的三个值
    private int currentPage ;

    private int pageSize;

    private int start;


    //获取偏移量
    public int getStart() {
        return (currentPage-1)*pageSize;
    }





}
