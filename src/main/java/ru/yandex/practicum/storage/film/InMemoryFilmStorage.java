package ru.yandex.practicum.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NoSuchFilmException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private final List<Film> filmList = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private final FilmValidation filmValidation = new FilmValidation();
    private int generatedId = 1;

    @Override
    public Film saveFilm(Film film) {
        if (!filmValidation.isValid(film)) {
            log.warn("Ошибка валидации при добавлении фильма" + film);
            throw new ValidationException("Ошибка валидации при добавлении фильма");
        } else {
            film.setId(generatedId++);
            films.put(film.getId(), film);
            log.debug("Успешное добавление фильма");
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!filmValidation.isValid(film)) {
            log.warn("Ошибка валидации при обновлении фильма " + film);
            throw new ValidationException("Ошибка валидации при обновлении фильма");
        } else {
            if (films.containsKey(film.getId())) {
                films.remove(film.getId());
                films.put(film.getId(), film);
                log.debug("Успешное обновлении фильма");
            } else {
                log.warn("Невозможно обновлении, не существует фильма " + film);
                throw new NoSuchFilmException("Нет такого фильма");
            }
        }
        return film;
    }

    @Override
    public Film deleteFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
        } else {
            log.warn("Невозможно удаление так как не существует фильм " + film);
            throw new NoSuchFilmException("нет такого фильма");
        }
        return film;
    }


    @Override
    public HashMap<Integer, Film> getAllFilmsMap() {
        return films;
    }

    private List<Film> toListFilms() {
        filmList.addAll(films.values());
        return filmList;
    }
}
