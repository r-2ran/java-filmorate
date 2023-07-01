package ru.yandex.practicum;

import ru.yandex.practicum.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.validation.FilmValidation;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmValidationTest {
    Film film;
    FilmValidation validation = new FilmValidation();
    @BeforeEach
    public void createFilm() {
        film = new Film(1, "name", "description",
                LocalDate.of(2010, 1, 1), Duration.ofMinutes(120));
    }
    @Test
    public void wrongName() {
        film.setName("");
        assertFalse(validation.isValid(film));
    }
    @Test
    public void wrongDescription() {
        film.setDescription("Двое болтливых убийц (Джон Траволта и Сэмюэль Л.Джексон) решают" +
                " разнообразные проблемы своего босса Марселласа Уоллеса. «Чтиво» — вне всякого сомнения," +
                " главный фильм 1990-х, и это единственное десятилетие в истории, у которого есть столь явный чемпион." +
                " Тарантино навсегда изменил саму суть киноязыка — как грамматику, так и орфографию, — в свете" +
                " наступившего постмодернизма." +
                " А прокатывавшая «Чтиво» «Мирамакс» навсегда изменила правила игры в независимом кино. ");
        assertFalse(validation.isValid(film));
    }
    @Test
    public void wrongReleaseDate() {
        film.setReleaseDate(LocalDate.of(1024,1,1));
        assertFalse(validation.isValid(film));
    }
    @Test
    public void okTest() {
        assertTrue(validation.isValid(film));
    }
}
