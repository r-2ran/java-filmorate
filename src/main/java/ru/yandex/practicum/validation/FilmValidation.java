package ru.yandex.practicum.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;

import java.time.LocalDate;
import java.time.Month;

@Component
public class FilmValidation {
    public boolean isValid(Film film) {
      //  setMpaName(film);
      //  setGenres(film);
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

    private boolean genreDuplicateCheck(Film film) {
        boolean result = true;
        for (int i = 0; i < film.getGenres().size(); i++) {
            if (film.getGenres().contains(film.getGenres().get(i))) {
                result = false;
                break;
            }
        }
        return result;
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

    private void setGenres(Film film) {
        int genres = film.getGenres().size();
        for (Genre genre : film.getGenres()) {
            switch (genre.getId()) {
                case 1 : genre.setName("Комедия");
                break;
                case 2 : genre.setName("Драма");
                break;
                case 3 : genre.setName("Мультфильм");
                break;
                case 4 : genre.setName("Триллер");
                break;
                case 5 : genre.setName("Документальный");
                break;
                case 6 : genre.setName("Боевик");
                break;
            }
        }
    }
}
