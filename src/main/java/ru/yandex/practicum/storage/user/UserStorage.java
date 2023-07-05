package ru.yandex.practicum.storage.user;


import ru.yandex.practicum.model.User;

public interface UserStorage {
    void saveUser(User user);

    void deleteUser(User user);

    void updateUser(User user);
}
