package ru.yandex.practicum.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.NoSuchFilmException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final FilmValidation validation = new FilmValidation();
    private Integer generatedId = 1;

    @GetMapping
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        if (!validation.isValid(film)) {
            log.warn("Ошибка валидации при добавлении фильма");
            throw new ValidationException("Ошибка валидации при добавлении фильма");
        } else {
            film.setId(generatedId++);
            films.put(film.getId(), film);
            log.debug("Успешное добавление фильма");
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException, NoSuchFilmException {
        if (!validation.isValid(film)) {
            log.warn("Ошибка валидации при обновлении фильма " + film);
            throw new ValidationException("Ошибка валидации при обновлении фильма");
        } else {
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                log.debug("Успешное обновлении фильма");
            } else {
                log.warn("Невозможно обновлении, не существует фильма " + film);
                throw new NoSuchFilmException("Нет такого фильма");
            }
        }
        return film;
    }
}
