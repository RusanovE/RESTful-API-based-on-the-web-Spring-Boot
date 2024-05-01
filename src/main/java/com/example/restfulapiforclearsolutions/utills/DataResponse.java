package com.example.restfulapiforclearsolutions.utills;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> {
    private T data;

    public DataResponse(T data) {
        this.data = data;
    }

}

