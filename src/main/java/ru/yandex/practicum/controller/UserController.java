package ru.yandex.practicum.controller;

import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.validation.UserValidation;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    List<User> users = new ArrayList<>();
    UserValidation validation = new UserValidation();

    @GetMapping("/users")
    public List<User> allUsers() {
        return users;
    }
    @PostMapping("/users")
    public List<User> addUser(User user) throws ValidationException {
        if(!validation.isValid(user)) {
            log.warn("Ошибка валидации при добавлении пользователя");
            throw new ValidationException("Ошибка валидации при добавлении пользователя");
        } else {
            users.add(user);
            log.debug("Успешное добавление пользователя");
        }
        return users;
    }
    @PutMapping("/users")
    public List<User> updateFilm(User user) throws ValidationException {
        if(!validation.isValid(user)) {
            log.warn("Ошибка валидации при обновлении пользователя");
            throw new ValidationException("Ошибка валидации при обновлении пользователя");
        } else {
            for (User userCurrent : users) {
                if (userCurrent.getId() == user.getId()) {
                    users.remove(userCurrent);
                    users.add(user);
                    log.debug("Успешное обновление пользователя");
                }
            }
        }
        return users;
    }
}
