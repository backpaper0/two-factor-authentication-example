package com.example.user;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.otp.TimeBasedOneTimePasswordGenerator;

public class User {

    private static final TimeBasedOneTimePasswordGenerator otpGenerator = TimeBasedOneTimePasswordGenerator
            .builder()
            .algorithm("HmacSHA1")
            .build();

    private final String username;
    private final String password;
    private final boolean twoFactorAuthN;
    private final byte[] key;

    public User(final String username, final String password, final boolean twoFactorAuthN,
            final byte[] key) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.twoFactorAuthN = twoFactorAuthN;
        this.key = key;
    }

    public static User get(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (User) session.getAttribute(User.class.getName());
    }

    public void setTo(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.setAttribute(User.class.getName(), this);
    }

    public String getUsername() {
        return username;
    }

    public boolean isTwoFactorAutheN() {
        return twoFactorAuthN;
    }

    public boolean testPassword(final String testMe) {
        return password.equals(testMe);
    }

    public User withTwoFactorAuthentication(final boolean twoFactorAuthN) {
        return new User(username, password, twoFactorAuthN, key);
    }

    public User copy() {
        return new User(username, password, twoFactorAuthN, key);
    }

    public boolean testTwoFactorAuthN(final String code) {
        final int generated = otpGenerator.generate(key);
        final String formatted = String.format("%06d", generated);
        return formatted.equals(code);
    }
}
