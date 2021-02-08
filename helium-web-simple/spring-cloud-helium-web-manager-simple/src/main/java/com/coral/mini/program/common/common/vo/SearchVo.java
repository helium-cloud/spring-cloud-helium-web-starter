package com.coral.mini.program.common.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author coral
 */
@Data
public class SearchVo implements Serializable {

    private String startDate;

    private String endDate;

    private int pageNumber;

    private int pageSize;

    private String cardType;

    private String number;

    private int start;

    public int getStart() {
        return (pageNumber-1)*pageSize;
    }

    public void setParam(){
        setStartDate(this.startDate+=" 00:00:00");
        setEndDate(this.endDate+=" 23:59:59");
    }
}
