package ru.yandex.practicum.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;

@Component
public class UserValidation {
    public boolean isValid(User user) {
        if (user.getName().isBlank() && loginCheck(user)) {
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
        return !user.getLogin().isBlank();
    }

    private boolean birthdayCheck(User user) {
        return user.getBirthday().isBefore(LocalDate.now());
    }

}

