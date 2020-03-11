package com.example.user;

import java.security.Principal;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class User implements Principal {

    private final String username;
    private final String password;
    private final boolean twoFactorAuthentication;

    public User(final String username, final String password,
            final boolean twoFactorAuthentication) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.twoFactorAuthentication = twoFactorAuthentication;
    }

    public static User get(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (User) session.getAttribute(User.class.getName());
    }

    public void setTo(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.setAttribute(User.class.getName(), this);
    }

    @Override
    public String getName() {
        return username;
    }

    public boolean isTwoFactorAuthentication() {
        return twoFactorAuthentication;
    }

    public boolean testPassword(final String testMe) {
        return password.equals(testMe);
    }

    public User withTwoFactorAuthentication(final boolean twoFactorAuthentication) {
        return new User(username, password, twoFactorAuthentication);
    }

    public User copy() {
        return new User(username, password, twoFactorAuthentication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, twoFactorAuthentication);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() != getClass()) {
            return false;
        }
        final User other = (User) obj;
        return username.equals(other.username)
                && password.equals(other.password)
                && twoFactorAuthentication == other.twoFactorAuthentication;
    }
}
