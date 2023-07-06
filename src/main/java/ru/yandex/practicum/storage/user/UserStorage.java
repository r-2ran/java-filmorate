package ru.yandex.practicum.storage.user;


import ru.yandex.practicum.model.User;

public interface UserStorage {
    User saveUser(User user);

    User deleteUser(User user);

    User updateUser(User user);
}
