package com.coral.mini.program.common.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author coral
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

