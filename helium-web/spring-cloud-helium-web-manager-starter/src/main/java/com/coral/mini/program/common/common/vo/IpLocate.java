package com.coral.mini.program.common.common.vo;



import java.io.Serializable;

/**
 * @author coral
 */

public class IpLocate implements Serializable {

    private String retCode;

    private City result;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public City getResult() {
        return result;
    }

    public void setResult(City result) {
        this.result = result;
    }
}

