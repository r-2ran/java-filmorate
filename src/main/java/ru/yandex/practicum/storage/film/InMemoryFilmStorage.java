package ru.yandex.practicum.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NoSuchFilmException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;
import ru.yandex.practicum.model.film.Mpa;
import ru.yandex.practicum.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private final FilmValidation filmValidation = new FilmValidation();
    private int generatedId = 1;
    private final List<Mpa> mpas = new ArrayList<>(List.of(new Mpa(1, "G"), new Mpa(2, "PG"),
            new Mpa(3, "PG-13"), new Mpa(4, "R"), new Mpa(5, "NC-17")));

    private final List<Genre> genres = new ArrayList<>(List.of(new Genre(1, "Комедия"),
            new Genre(2, "Драма"),
            new Genre(3, "Мультфильм"), new Genre(4, "Триллер"),
            new Genre(5, "Документальный"), new Genre(6, "Боевик")));

    @Override
    public Film saveFilm(Film film) {
        if (!filmValidation.isValid(film)) {
            log.warn("Ошибка валидации при добавлении фильма" + film);
            throw new ValidationException("Ошибка валидации при добавлении фильма");
        } else {
            if (film.getMpa() != null) {
                setMpaName(film);
            }
            if (film.getGenres() != null) {
                setGenresName(film);
            }
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
                setGenresName(film);
                setMpaName(film);
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
    public void deleteFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
        } else {
            log.warn("Невозможно удаление так как не существует фильм " + film);
            throw new NoSuchFilmException("нет такого фильма");
        }
    }

    public List<Mpa> getAllMpa() {
        return mpas;
    }

    @Override
    public HashMap<Integer, Film> getAllFilmsMap() {
        return films;
    }

    public List<Genre> getAllGenres() {
        return genres;
    }

    private void setMpaName(Film film) {
        int mpaId = film.getMpa().getId();
        switch (mpaId) {
            case 1:
                film.getMpa().setName("G");
                break;
            case 2:
                film.getMpa().setName("PG");
                break;
            case 3:
                film.getMpa().setName("PG-13");
                break;
            case 4:
                film.getMpa().setName("R");
                break;
            case 5:
                film.getMpa().setName("NC-17");
                break;
        }
    }

    private void setGenresName(Film film) {
        int genres = film.getGenres().size();
        for (Genre genre : film.getGenres()) {
            switch (genre.getId()) {
                case 1:
                    genre.setName("Комедия");
                    break;
                case 2:
                    genre.setName("Драма");
                    break;
                case 3:
                    genre.setName("Мультфильм");
                    break;
                case 4:
                    genre.setName("Триллер");
                    break;
                case 5:
                    genre.setName("Документальный");
                    break;
                case 6:
                    genre.setName("Боевик");
                    break;
            }
        }
    }
}
