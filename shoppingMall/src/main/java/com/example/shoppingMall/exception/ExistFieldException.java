package com.example.shoppingMall.exception;

public class ExistFieldException extends RuntimeException{
    public ExistFieldException() {
        super();
    }

    public ExistFieldException(String message) {
        super(message);
    }

    public ExistFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistFieldException(Throwable cause) {
        super(cause);
    }

    protected ExistFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
