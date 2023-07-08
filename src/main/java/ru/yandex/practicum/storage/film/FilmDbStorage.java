package ru.yandex.practicum.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.film.Film;
import ru.yandex.practicum.model.film.Genre;
import ru.yandex.practicum.model.film.Mpa;

import java.util.HashMap;

@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film saveFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("films")
                .usingGeneratedKeyColumns("id");

        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public Film deleteFilm(Film film) {
        return null;
    }

    @Override
    public HashMap<Integer, Film> getAllFilmsMap() {
      //  return jdbcTemplate.query("select * from films", filmRowMapper());
        return null;
    }

    private RowMapper<Film> filmRowMapper() {
        return ((rs, rowNum) -> new Film(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("release_date").toLocalDateTime().toLocalDate(),
                rs.getInt("likes_id")));
    }

    private RowMapper<Genre> getGenre() {
        return ((rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                rs.getString("genre_name")
        ));
    }

    private RowMapper<Mpa> getMpa() {
        return ((rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("genre_name")
                ));
    }
}