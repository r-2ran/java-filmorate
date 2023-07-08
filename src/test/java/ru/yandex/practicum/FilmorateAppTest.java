package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.user.User;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateAppTest {
    private final InMemoryUserStorage userStorage;
    private final InMemoryFilmStorage filmStorage;

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(1);
        userStorage.saveUser(user);
        assertEquals(user.getId(), userStorage.getUser(1).getId());
    }

    @Test
    public void testGetFilmById() {
        Film film = new Film();
        film.setId(1);
        filmStorage.saveFilm(film);
        assertEquals(film.getId(), filmStorage.getFilmById(1).getId());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        userStorage.saveUser(user1);
        userStorage.saveUser(user2);
        assertEquals(2, userStorage.getAllUsers().size());
    }

    @Test
    public void testGetAllFilms() {
        Film film1 = new Film();
        film1.setId(1);
        Film film2 = new Film();
        film2.setId(2);
        filmStorage.saveFilm(film1);
        filmStorage.saveFilm(film2);
        assertEquals(2, filmStorage.getAllFilmsMap().size());
    }

    @Test
    void contextLoads() {
    }
}