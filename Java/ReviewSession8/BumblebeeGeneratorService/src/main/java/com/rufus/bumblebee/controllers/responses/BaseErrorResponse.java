package com.rufus.bumblebee.controllers.responses;

public class BaseErrorResponse<M> {

    private M errorMessage;

    public M getErrorMessage() {
        return errorMessage;
    }

    public BaseErrorResponse setErrorMessage(M errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
