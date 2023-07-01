package ru.yandex.practicum;

import ru.yandex.practicum.model.User;
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
        user.setBirthday(LocalDate.of(2024,1,1));
        assertFalse(validation.isValid(user));
    }
    @Test
    public void okTest() {
        assertTrue(validation.isValid(user));
    }
}
