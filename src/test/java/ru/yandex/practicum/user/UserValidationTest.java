package ru.yandex.practicum.user;

import ru.yandex.practicum.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.validation.UserValidation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
    User user;
    UserValidation validation = new UserValidation();

    @BeforeEach
    public void createUser() {
        user = new User(1, "pochta@mail.ru", "login", "name",
                LocalDate.of(1995, 1, 1));
    }

    @Test
    public void wrongEmail() {
        user.setEmail("pochta.sobaka.ru");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void wrongLogin() {
        user.setLogin("");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void wrongBirthday() {
        user.setBirthday(LocalDate.of(2024, 1, 1));
        assertFalse(validation.isValid(user));
    }

    @Test
    public void emptyName() {
        User emptyNameUser = new User("friend@common.ru", "common",
                LocalDate.of(2000, 12, 12));
        try {
            assertTrue(validation.isValid(emptyNameUser));
        } catch (NullPointerException e) {
            emptyNameUser.setName(emptyNameUser.getName());
        }
    }

    @Test
    public void okTest() {
        assertTrue(validation.isValid(user));
    }
}
