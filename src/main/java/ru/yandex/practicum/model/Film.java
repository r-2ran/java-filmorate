package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
}
