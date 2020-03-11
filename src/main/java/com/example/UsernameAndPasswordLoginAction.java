package com.example;

import java.security.Principal;

import com.example.lib.LoginFilter.LoginAction;

public class UsernameAndPasswordLoginAction implements LoginAction {

    @Override
    public Principal login(final String username, final String password) {
        return Users.find(username).filter(a -> a.testPassword(password)).orElse(null);
    }
}
