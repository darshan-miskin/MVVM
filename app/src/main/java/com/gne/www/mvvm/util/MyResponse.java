package com.gne.www.mvvm.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyResponse<T> {
    private Status status;
    private String errorMessage;
    private T responseBody;

    public Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    public T getData() {
        return responseBody;
    }

    public void setData(@Nullable T responseBody) {
        this.responseBody = responseBody;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
