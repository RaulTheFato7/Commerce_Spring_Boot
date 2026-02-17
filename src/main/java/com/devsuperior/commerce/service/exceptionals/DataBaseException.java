package com.devsuperior.commerce.service.exceptionals;

public class DataBaseException extends RuntimeException{

    public DataBaseException(String msg) {
        super(msg);
    }
}
