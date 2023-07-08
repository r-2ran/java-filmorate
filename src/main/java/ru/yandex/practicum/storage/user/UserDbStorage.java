package ru.yandex.practicum.storage.user;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.user.User;

import java.util.List;

@Repository
@Qualifier
public class UserDbStorage implements UserStorage {
    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
