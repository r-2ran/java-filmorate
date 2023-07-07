package ru.yandex.practicum.exception;

public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException(String message) {
        super();
    }
}
