package com.appsbykeegan.watcher_storageapi.entity.models;

import lombok.Data;

/**
 * Created by Kelvin Keegan on 5/25/2022
 */

@Data
public class DeleteRequestBody {

    private String fileName;
    private String dateTime;
    private String driverLetter;

}
