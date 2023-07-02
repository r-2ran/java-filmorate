package ru.yandex.practicum.exception;

public class NoSuchFilmException extends Throwable {
    private String message;

    public NoSuchFilmException(String message) {
        super();
    }
}
