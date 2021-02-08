package com.coral.mini.program.common.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author coral
 */
@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}
