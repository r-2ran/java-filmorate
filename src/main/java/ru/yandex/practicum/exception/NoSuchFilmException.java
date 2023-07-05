package ru.yandex.practicum.exception;

public class NoSuchFilmException extends RuntimeException {
    private String message;

    public NoSuchFilmException(String message) {
        super();
    }
}
