package ru.yandex.practicum.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final HashMap<Integer, User> users = new HashMap<>();
    private final UserValidation validation = new UserValidation();
    private Integer generatedId = 1;

    @GetMapping
    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws ValidationException, NullPointerException {
        try {
            if (!validation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            users.put(user.getId(), user);
            log.debug("Успешное добавление пользователя");
        } catch (NullPointerException e) {
            user.setName(user.getLogin());
            if (!validation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            users.put(user.getId(), user);
            log.debug("Успешное добавление пользователя");
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (!validation.isValid(user)) {
            log.warn("Ошибка валидации при обновлении пользователя " + user);
            throw new ValidationException("Ошибка валидации при обновлении пользователя");
        } else {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.debug("Успешное обновление пользователя");
            } else {
                log.warn("Невозможно обновить,  пользователя " + user + " не существует");
                throw new ValidationException("Нет такого пользователя");
            }
        }
        return user;
    }
}
