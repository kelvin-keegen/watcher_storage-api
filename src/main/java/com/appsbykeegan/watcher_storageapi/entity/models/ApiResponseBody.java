package com.appsbykeegan.watcher_storageapi.entity.models;

import lombok.Data;

/**
 * Created by Kelvin Keegan on 5/24/2022
 */

@Data
public class ApiResponseBody {

    private int statusCode;
    private String message;
    private Object data;


    public ApiResponseBody(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
