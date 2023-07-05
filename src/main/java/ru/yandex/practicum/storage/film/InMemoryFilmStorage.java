package ru.yandex.practicum.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NoSuchFilmException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.validation.FilmValidation;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    public HashMap<Integer, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private final FilmValidation filmValidation = new FilmValidation();

    @Override
    public void saveFilm(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public void updateFilm(Film film) {

        if (!filmValidation.isValid(film)) {
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
    }

    @Override
    public void deleteFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
        } else {
            log.warn("Невозможно удаление так как не существует фильм");
            throw new NoSuchFilmException("нет такого фильма");
        }
    }
}
