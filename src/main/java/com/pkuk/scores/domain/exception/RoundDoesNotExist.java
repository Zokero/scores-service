package com.pkuk.scores.domain.exception;

public class RoundDoesNotExist extends RuntimeException {
    public RoundDoesNotExist(String message) {
        super(message);
    }
}
