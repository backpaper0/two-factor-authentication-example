package com.example;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class Users {

    private static final Set<User> users = new HashSet<>();

    static {
        save(new User("foo", "secret", true));
        save(new User("bar", "secret", true));
    }

    public static synchronized Optional<User> find(final String username) {
        return users.stream().filter(byUsername(username)).findAny().map(User::copy);
    }

    public static synchronized void save(final User user) {
        users.removeIf(byUsername(user.getName()));
        users.add(user);
    }

    private static Predicate<User> byUsername(final String username) {
        return a -> a.getName().equals(username);
    }
}
