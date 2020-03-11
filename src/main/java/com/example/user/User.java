package com.example.user;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.otp.TimeBasedOneTimePasswordGenerator;

public class User {

    private static final TimeBasedOneTimePasswordGenerator otpGenerator = TimeBasedOneTimePasswordGenerator
            .builder()
            .algorithm("HmacSHA1")
            .build();

    private static final SecureRandom otpKeyGenerator;
    static {
        try {
            //cf. https://docs.oracle.com/javase/jp/8/docs/technotes/guides/security/StandardNames.html#SecureRandom
            otpKeyGenerator = SecureRandom.getInstance("SHA1PRNG");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("ありえない", e);
        }
    }

    private final String username;
    private final String password;
    private final boolean twoFactorAuthN;
    private final byte[] key;

    public User(final String username, final String password, final boolean twoFactorAuthN,
            final byte[] key) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.twoFactorAuthN = twoFactorAuthN;
        if (twoFactorAuthN) {
            Objects.requireNonNull(key);
        }
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

    public boolean isTwoFactorAuthN() {
        return twoFactorAuthN;
    }

    public boolean testPassword(final String testMe) {
        return password.equals(testMe);
    }

    public User withTwoFactorAuthentication(final boolean newTwoFactorAuthN) {
        final byte[] newKey = newTwoFactorAuthN ? generateKey() : null;
        return new User(username, password, newTwoFactorAuthN, newKey);
    }

    private byte[] generateKey() {
        final byte[] key = new byte[20];
        otpKeyGenerator.nextBytes(key);
        return key;
    }

    public User copy() {
        return new User(username, password, twoFactorAuthN, key);
    }

    public boolean testOTP(final String otp) {
        final int generated = otpGenerator.generate(key);
        final String formatted = String.format("%06d", generated);
        return formatted.equals(otp);
    }

    public String getKeyAsHex() {
        final StringBuilder buf = new StringBuilder();
        for (final byte b : key) {
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }
}
