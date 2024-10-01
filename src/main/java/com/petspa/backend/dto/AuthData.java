package com.petspa.backend.dto;

import com.petspa.backend.entity.Account;

public class AuthData {
    private Account account;
    private String jwt;
    private String refreshToken;

    public AuthData(Account account, String jwt, String refreshToken) {
        this.account = new Account();
        this.account.setId(account.getId());
        this.account.setEmail(account.getEmail());
        this.account.setName(account.getName());
        // this.account.setAvatarUrl(account.getAvatarUrl());
        this.account.setRoles(account.getRoles());
        this.account.setCreatedAt(account.getCreatedAt());
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
