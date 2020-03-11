package com.example;

import java.security.Principal;
import java.util.Objects;

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
