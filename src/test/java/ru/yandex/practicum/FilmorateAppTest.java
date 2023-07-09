package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;
import ru.yandex.practicum.model.film.Mpa;
import ru.yandex.practicum.model.user.User;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateAppTest {
    private final InMemoryUserStorage userStorage;
    private final InMemoryFilmStorage filmStorage;
    private User user1;
    private User user2;

    private Film film1;
    private Film film2;


    @BeforeEach
    public void createUser() {
        user1 = new User(1, "pochta@mail.ru", "login", "name",
                LocalDate.of(1995, 1, 1));
        user2 = new User(2, "1234@mail.ru", "logi1334n", "n33ame",
                LocalDate.of(2005, 1, 1));
    }

    @BeforeEach
    public void createFilm() {

        film1 = new Film(1, "123nam4", "description   1234",
                LocalDate.of(2000, 1, 1), 120);
        film1.setMpa(new Mpa(1, "P"));
        film1.setGenres(new LinkedHashSet<>(Set.of(new Genre(1, "Комедия"))));

        film2 = new Film(2, "name", "description",
                LocalDate.of(2010, 1, 1), 120);
        film2.setMpa(new Mpa(1, "P"));
        film2.setGenres(new LinkedHashSet<>(Set.of(new Genre(1, "Комедия"))));
    }

    @Test
    public void testFindUserById() {
        assertEquals(1, userStorage.getUser(1).getId());
    }

    @Test
    public void testGetFilmById() {
        assertEquals(1, filmStorage.getFilmById(1).getId());
    }

    @Test
    public void testGetAllUsers() {
        userStorage.saveUser(user1);
        userStorage.saveUser(user2);
        assertEquals(2, userStorage.getAllUsers().size());
    }

    @Test
    public void testGetAllFilms() {
        filmStorage.saveFilm(film1);
        filmStorage.saveFilm(film2);
        assertEquals(2, filmStorage.getAllFilmsMap().size());
    }

    @Test
    void contextLoads() {
    }
}