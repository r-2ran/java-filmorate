package ru.yandex.practicum.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NoSuchFilmException;
import ru.yandex.practicum.exception.NoSuchUserException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;
import ru.yandex.practicum.model.film.Mpa;
import ru.yandex.practicum.service.user.UserService;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.validation.FilmValidation;
import ru.yandex.practicum.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final FilmValidation filmValidation;
    private final UserValidation userValidation;
    private final UserService userService;


    @Autowired
    public FilmService(@Qualifier("inMemoryFilmStorage")
                       InMemoryFilmStorage filmStorage,
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
        return new ArrayList<>(filmStorage.getAllFilmsMap().get(filmId).getLikes());
    }

    public void deleteLike(int filmId, int userId) {
        if (userService.containsUser(userId)) {
            if (filmValidation.isValid(getFilmById(filmId)) && userValidation.isValid(userService.getUser(userId))) {
                getLikesByFilmId(filmId).remove((long) userId);
                filmStorage.updateFilm(getFilmById(filmId));
            } else {
                throw new ValidationException("validation error " + filmId);
            }
        } else {
            throw new NoSuchUserException("no such user" + userId);
        }
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films = new ArrayList<>(filmStorage.getAllFilmsMap().values());
        if (films.size() < count) {
            count = films.size();
        }
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
        List<Film> films = new ArrayList<>(filmStorage.getAllFilmsMap().values());
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
        return new ArrayList<>(filmStorage.getAllFilmsMap().values());
    }

    public Film getFilm(int id) {
        if (filmStorage.getAllFilmsMap().containsKey(id)) {
            return filmStorage.getAllFilmsMap().get(id);
        } else {
            log.warn("нет такого фильма " + getFilmById(id));
            throw new NoSuchFilmException("нет такого фильма " + getFilmById(id));
        }
    }

    public Film saveFilm(Film film) {
        return filmStorage.saveFilm(film);
    }

    private Film getFilmById(int filmId) {  // получить объект Film по id
        if (filmStorage.getAllFilmsMap().containsKey(filmId)) {
            return filmStorage.getAllFilmsMap().get(filmId);
        } else {
            throw new NoSuchFilmException("нет такого фильма " + filmStorage.getAllFilmsMap().get(filmId));
        }
    }

    private HashSet<Long> getLikesByFilmId(int id) {
        return filmStorage.getAllFilmsMap().get(id).getLikes();
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Mpa> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Mpa getMpa(int id) {
        for (Mpa mpa : getAllMpa()) {
            if (mpa.getId() == id) {
                return mpa;
            } else {
                throw new NoSuchFilmException("no such mpa " + mpa);
            }
        }
        return null;
    }

    public List<Genre> getAllGenre() {
        return filmStorage.getAllGenres();
    }

    public Genre getGenreById(int id) {
        for (Genre genre : getAllGenre()) {
            if (genre.getId() == id) {
                return genre;
            } else {
                throw new NoSuchFilmException("no such genre " + genre);
            }
        }
        return null;
    }
}
