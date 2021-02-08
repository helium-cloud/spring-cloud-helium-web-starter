package com.coral.mini.program.common.common.vo;

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


    public String getEcNo() {
        return ecNo;
    }

    public void setEcNo(String ecNo) {
        this.ecNo = ecNo;
    }

    public String getSelectCondition() {
        return selectCondition;
    }

    public void setSelectCondition(String selectCondition) {
        this.selectCondition = selectCondition;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getChatbotId() {
        return chatbotId;
    }

    public void setChatbotId(String chatbotId) {
        this.chatbotId = chatbotId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
