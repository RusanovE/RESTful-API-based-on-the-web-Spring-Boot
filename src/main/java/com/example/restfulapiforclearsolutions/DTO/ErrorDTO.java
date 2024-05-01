package com.example.restfulapiforclearsolutions.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDTO {

    int status;

    String title;

    String message;

    Date timestamp;

    public ErrorDTO(int status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.timestamp = new Date();
    }
}
