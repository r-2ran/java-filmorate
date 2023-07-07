package ru.yandex.practicum.exception;

public class NoSuchUserException extends RuntimeException {
    private String message;

    public NoSuchUserException(String message) {
        super();
    }
}
