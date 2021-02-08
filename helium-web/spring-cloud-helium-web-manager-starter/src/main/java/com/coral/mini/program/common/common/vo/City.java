package com.coral.mini.program.common.common.vo;


import java.io.Serializable;

/**
 * @author coral
 */
public class City implements Serializable {

    String country;

    String province;

    String city;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
