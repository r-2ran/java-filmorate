package ru.yandex.practicum.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NoSuchUserException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.user.User;
import ru.yandex.practicum.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private final UserValidation userValidation = new UserValidation();
    private int generatedId = 1;


    @Override
    public User saveUser(User user) {
        try {
            if (!userValidation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            users.put(user.getId(), user);
            log.debug("Успешное добавление пользователя");
        } catch (NullPointerException e) {
            user.setName(user.getLogin());
            if (!userValidation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            users.put(user.getId(), user);
            log.debug("Успешное добавление пользователя");
        }
        return users.get(user.getId());
    }

    @Override
    public void deleteUser(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
        } else {
            log.warn("Невозможно удаление так как нет такого пользователя " + user);
            throw new NoSuchUserException("нет такого пользователя");
        }
    }

    @Override
    public User updateUser(User user) {
        if (!userValidation.isValid(user)) {
            log.warn("Ошибка валидации при обновлении пользователя " + user);
            throw new ValidationException("Ошибка валидации при обновлении пользователя");
        } else {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.debug("Успешное обновление пользователя");
            } else {
                log.warn("Невозможно обновить,  пользователя " + user + " не существует");
                throw new NoSuchUserException("Нет такого пользователя");
            }
        }
        return users.get(user.getId());
    }

    public HashMap<Integer, User> getUsersMap() {
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
