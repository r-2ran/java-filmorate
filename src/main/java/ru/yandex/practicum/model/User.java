package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

//    public User(int id, String email, String login, LocalDate birthday) {
//        this.id = id;
//        this.email = email;
//        this.login = login;
//        this.birthday = birthday;
//    }
}
