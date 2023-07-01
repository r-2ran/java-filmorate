package ru.yandex.practicum.validation;

import ru.yandex.practicum.model.User;

import java.time.LocalDate;

public class UserValidation {
    public boolean isValid(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return emailCheck(user)
                && loginCheck(user)
                && birthdayCheck(user);
    }

    private boolean emailCheck(User user) {
        return !user.getEmail().isEmpty() && user.getEmail().contains(String.valueOf("@"));
    }

    private boolean loginCheck(User user) {
        return !user.getLogin().isEmpty();
    }

    private boolean birthdayCheck(User user) {
        return user.getBirthday().isBefore(LocalDate.now());
    }

}

