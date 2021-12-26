package com.alpha.common.util.token;

import java.io.Serializable;

public class LoginInfoEntity implements Serializable {

    private String userId;

    private String userName;

    private boolean isLogin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

}
