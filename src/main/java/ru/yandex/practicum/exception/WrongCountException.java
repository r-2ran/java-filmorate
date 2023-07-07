package ru.yandex.practicum.exception;

public class WrongCountException extends RuntimeException {
    public WrongCountException(String message) {
        super(message);
    }
}
