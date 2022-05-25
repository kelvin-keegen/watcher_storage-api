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
    private String driveLetterName;

    public DeleteRequestBody(String fileName, String dateTime, String driverLetter, String driveLetterName) {
        this.fileName = fileName;
        this.dateTime = dateTime;
        this.driverLetter = driverLetter;
        this.driveLetterName = driveLetterName;
    }
}
