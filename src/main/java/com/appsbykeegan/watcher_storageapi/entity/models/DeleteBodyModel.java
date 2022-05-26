package com.appsbykeegan.watcher_storageapi.entity.models;

import lombok.Data;

/**
 * Created by Kelvin Keegan on 5/26/2022
 */

@Data
public class DeleteBodyModel {

    private String fileName;
    private String dateTime;
    private String driverLetter;
    private String usbDriveName;

}
