package ru.yandex.practicum.exception;

public class NoSuchUserException extends Throwable {
    private String message;

    public NoSuchUserException(String message) {
        super();
    }
}
