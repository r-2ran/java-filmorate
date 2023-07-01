package ru.yandex.practicum.exception;

public class ValidationException extends Throwable{
    private String message;
    public ValidationException(String message) {
        super();
    }
}
