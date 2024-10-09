package com.example.dt.AUTH.Models;

public class AuthResponse {

    private boolean isExist;
    private String message;

    public AuthResponse(boolean isExist, String message) {
        this.isExist = isExist;
        this.message = message;
    }

    public boolean isExist() {
        return isExist;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SignupResponse{" +
                "isExist=" + isExist +
                ", message='" + message + '\'' +
                '}';
    }
}
