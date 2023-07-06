package ru.yandex.practicum.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.user.UserService;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.validation.FilmValidation;
import ru.yandex.practicum.validation.UserValidation;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final FilmValidation filmValidation;
    private final UserValidation userValidation;
    private final UserService userService;


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

    public void deleteLike(int filmId, int userId) {
        if (userService.containsUser(userId)) {
            if (filmValidation.isValid(getFilmById(filmId)) && userValidation.isValid(userService.getUser(userId))) {
                getLikesByFilmId(filmId).remove((long) userId);
                filmStorage.updateFilm(getFilmById(filmId));
            } else {
                throw new ValidationException("validation error");
            }
        } else {
            throw new NoSuchUserException("no such user" + userId);
        }
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films = new ArrayList<>(filmStorage.films.values());
        if (count == 1) {
            return mostPopularFilm();
        } else {
            return films.stream().sorted((f1, f2) -> {
                        Integer f1Likes = f1.getLikes().size();
                        Integer f2Likes = f2.getLikes().size();
                        return f2Likes.compareTo(f1Likes);
                    }
            ).limit(count).collect(Collectors.toList());
        }
    }

    private List<Film> mostPopularFilm() {
        List<Film> films = new ArrayList<>(filmStorage.films.values());
        Optional<Film> max = films.stream().min((f1, f2) -> {
            Integer f1Likes = f1.getLikes().size();
            Integer f2Likes = f2.getLikes().size();
            return f2Likes.compareTo(f1Likes);
        });
        List<Film> mostPopFilm = new ArrayList<>();
        mostPopFilm.add(max.get());
        return mostPopFilm;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmStorage.films.values());
    }

    public Film getFilm(int id) {
        if (filmStorage.films.containsKey(id)) {
            return filmStorage.films.get(id);
        } else {
            log.warn("нет такого фильма " + getFilmById(id));
            throw new NoSuchFilmException("нет такого фильма");
        }
    }

    public Film saveFilm(Film film) {
        return filmStorage.saveFilm(film);
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
        return filmStorage.updateFilm(film);
    }
}
