package ru.yandex.practicum.storage.user;


import ru.yandex.practicum.model.user.User;

import java.util.List;

public interface UserStorage {
    User saveUser(User user);

    User deleteUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();
}
