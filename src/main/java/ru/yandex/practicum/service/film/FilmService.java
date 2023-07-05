package ru.yandex.practicum.service.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.user.UserService;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.validation.FilmValidation;
import ru.yandex.practicum.validation.UserValidation;

import java.util.*;

@Service
public class FilmService {
    private int generatedId = 1;
    private final InMemoryFilmStorage filmStorage;
    private final FilmValidation filmValidation;
    private final UserValidation userValidation;
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(FilmService.class);


    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage,
                       FilmValidation filmValidation,
                       UserValidation userValidation,
                       UserService userService) {
        this.filmStorage = filmStorage;
        this.filmValidation = filmValidation;
        this.userValidation = userValidation;
        this.userService = userService;
    }

    public List<Long> likeFilm(int filmId, int userId) {
        getLikesByFilmId(filmId).add((long) userId);
        filmStorage.updateFilm(getFilmById(filmId));
        return new ArrayList<>(filmStorage.films.get(filmId).getLikes());
    }

    public List<Long> deleteLike(int filmId, int userId) {
        if (filmValidation.isValid(getFilmById(filmId)) && userValidation.isValid(userService.getUser(userId))) {
            getLikesByFilmId(filmId).remove((long) userId);
            filmStorage.updateFilm(getFilmById(filmId));
        } else {
            throw new ValidationException("error");
        }
        return new ArrayList<>(filmStorage.films.get(filmId).getLikes());
    }

    public List<Film> getPopularFilms(Integer count) {
        List<Film> buffer = new ArrayList<>(filmStorage.films.values());
        List<Film> mostLiked;
        if (count != null) {
            if (count <= 0) {
                throw new WrongCountException("wrong count");
            }
            mostLiked = new ArrayList<>(count);
            buffer.sort(Comparator.comparingInt(o -> o.getLikes().size()));
            for (int i = 0; i < count; i++) {
                mostLiked.add(buffer.get(i));
            }
        } else {
            throw new NoCountGiven();
        }
        return mostLiked;
    }


    public List<Film> getAllFilms() {
        return new ArrayList<>(filmStorage.films.values());
    }

    public Film getFilm(int id) {
        if (filmStorage.films.containsKey(id)) {
            return filmStorage.films.get(id);
        } else {
            throw new NoSuchFilmException("нет такого фильма");
        }
    }

    public Film addFilm(Film film) {
        if (!filmValidation.isValid(film)) {
            log.warn("Ошибка валидации при добавлении фильма");
            throw new ValidationException("Ошибка валидации при добавлении фильма");
        } else {
            film.setId(generatedId++);
            filmStorage.saveFilm(film);
            log.debug("Успешное добавление фильма");
        }
        return film;
    }

    private Film getFilmById(int filmId) {  // получить объект Film по id
        if (filmStorage.films.containsKey(filmId)) {
            return filmStorage.films.get(filmId);
        } else {
            throw new NoSuchFilmException("нет такого фильма");
        }
    }

    private HashSet<Long> getLikesByFilmId(int id) {
        return filmStorage.films.get(id).getLikes();
    }

    public Film updateFilm(Film film) {
        filmStorage.updateFilm(film);
        return filmStorage.films.get(film.getId());
    }

}
