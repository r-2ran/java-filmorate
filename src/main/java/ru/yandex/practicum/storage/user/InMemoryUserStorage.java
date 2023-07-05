package ru.yandex.practicum.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NoSuchUserException;
import ru.yandex.practicum.model.User;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    public HashMap<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);


    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
        } else {
            log.warn("Невозможно удаление так как нет такого пользователя");
            throw new NoSuchUserException("нет такого пользователя");
        }
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Невозможно обновление так как нет такого пользователя");
            throw new NoSuchUserException("нет такого пользователя");
        }
    }
}
