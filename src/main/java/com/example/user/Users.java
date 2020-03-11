package com.example.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Users {

    private static final Map<String, User> users = new HashMap<>();

    static {
        save(new User("foo", "secret", true, new byte[20]));
        save(new User("bar", "secret", false, null));
    }

    public static synchronized Optional<User> find(final String username) {
        final User user = users.get(username);
        return Optional.ofNullable(user).map(User::copy);
    }

    public static synchronized void save(final User user) {
        users.put(user.getUsername(), user);
    }
}
