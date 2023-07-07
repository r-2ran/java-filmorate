package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.Film;

public interface FilmStorage {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    Film deleteFilm(Film film);
}
