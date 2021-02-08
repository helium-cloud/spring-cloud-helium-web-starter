package org.helium.web.simple.bussiness.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import org.helium.web.common.business.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_suser")
@TableName("t_suser")
@ApiModel(value = "用户表")
public class SimpleUser extends BaseEntity {
    private String user;
    private String name;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
