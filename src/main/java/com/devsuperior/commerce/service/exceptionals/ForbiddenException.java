package com.devsuperior.commerce.service.exceptionals;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(String msg) {
        super(msg);
    }
}
