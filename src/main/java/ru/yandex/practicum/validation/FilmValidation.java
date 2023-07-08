package ru.yandex.practicum.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FilmValidation {
    public boolean isValid(Film film) {
        //setMpaName(film);
        // setGenresName(film);
        return nameCheck(film) &&
                descriptionCheck(film) &&
                releaseDateCheck(film) &&
                descriptionCheck(film) &&
                durationCheck(film);
              //  && genreDuplicateCheck(film);
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
        List<Genre> duplicates = film.getGenres().stream()
                //группируем в map (пользователь -> количество вхождений)
                .collect(Collectors.groupingBy(Function.identity()))
                //проходим по группам
                .entrySet()
                .stream()
                //отбираем пользователей, встречающихся более одного раза
                .filter(e -> e.getValue().size() > 1)
                //вытаскиваем ключи
                .map(Map.Entry::getKey)
                //собираем в список
                .collect(Collectors.toList());
        if (duplicates.size() > 0) {
            result = false;
        }
        return result;
    }


}
