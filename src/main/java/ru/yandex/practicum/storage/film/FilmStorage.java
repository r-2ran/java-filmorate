package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.Film;

public interface FilmStorage {
    void saveFilm(Film film);

    void updateFilm(Film film);

    void deleteFilm(Film film);
}
