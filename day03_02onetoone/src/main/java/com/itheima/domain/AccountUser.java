package com.itheima.domain;

import java.util.Date;

public class AccountUser extends Account{
    private String username;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AccountUser{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", uid=" + uid +
                ", money=" + money +
                '}';
    }
}
