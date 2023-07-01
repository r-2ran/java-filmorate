package ru.yandex.practicum.controller;

import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.validation.FilmValidation;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    List<Film> films = new ArrayList<>();
    FilmValidation validation = new FilmValidation();

    @GetMapping("/films")
    public List<Film> allFilms() {
        return films;
    }
    @PostMapping("/films")
    public List<Film> addFilm(Film film) throws ValidationException {
        if(!validation.isValid(film)) {
            log.warn("Ошибка валидации при добавлении фильма");
            throw new ValidationException("Ошибка валидации при добавлении фильма");
        }
        else {
            films.add(film);
            log.debug("Успешное добавление фильма");
        }
        return films;
    }
    @PutMapping("/films")
    public List<Film> updateFilm(Film film) throws ValidationException {
        if(!validation.isValid(film)) {
            log.warn("Ошибка валидации при обновлении фильма");
            throw new ValidationException("Ошибка валидации при обновлении фильма");
        }
        else {
            for (Film filmCurrent : films) {
                if (filmCurrent.getId() == film.getId()) {
                    films.remove(filmCurrent);
                    films.add(film);
                    log.debug("Успешное обновлении фильма");
                }
            }
        }
        return films;
    }
}
