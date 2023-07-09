package ru.yandex.practicum.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.film.Film;

import java.time.LocalDate;
import java.time.Month;

@Component
public class FilmValidation {
    public boolean isValid(Film film) {
        return nameCheck(film) &&
                descriptionCheck(film) &&
                releaseDateCheck(film) &&
                descriptionCheck(film) &&
                durationCheck(film);
    }

    private boolean nameCheck(Film film) {
        return !film.getName().isEmpty();
    }

    private boolean descriptionCheck(Film film) {
        return film.getDescription().length() <= 200;
    }

    private boolean releaseDateCheck(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.of(1895,
                Month.JANUARY, 28)) || film.getReleaseDate().isEqual(LocalDate.of(1895,
                Month.JANUARY, 28));
    }

    private boolean durationCheck(Film film) {
        return film.getDuration() > 0;
    }
}
