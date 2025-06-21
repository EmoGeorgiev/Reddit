package com.reddit.exception.handler;

public record ErrorObject(
        String message,
        int status) {
}
