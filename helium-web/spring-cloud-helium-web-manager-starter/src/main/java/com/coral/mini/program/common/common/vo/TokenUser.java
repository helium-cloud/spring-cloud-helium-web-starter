package com.coral.mini.program.common.common.vo;


import java.io.Serializable;
import java.util.List;

/**
 * @author coral
 */
public class TokenUser implements Serializable{

    private String username;

    private List<String> permissions;

    private Boolean saveLogin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Boolean getSaveLogin() {
        return saveLogin;
    }

    public void setSaveLogin(Boolean saveLogin) {
        this.saveLogin = saveLogin;
    }

    public TokenUser(String username, List<String> permissions, Boolean saveLogin) {
        this.username = username;
        this.permissions = permissions;
        this.saveLogin = saveLogin;
    }
}
