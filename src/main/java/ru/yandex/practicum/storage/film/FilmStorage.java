package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.film.Film;

import java.util.HashMap;

public interface FilmStorage {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(Film film);

    HashMap<Integer, Film> getAllFilmsMap();
}
