package com.appsbykeegan.watcher_storageapi.entity.models;

import lombok.Data;

/**
 * Created by Kelvin Keegan on 5/24/2022
 */

@Data
public class FileAttributeModel {

    private String fileName;
    private String dateAdded;
    private String lastModified;
    private String driveLetter;
    private String fileStatus;

}
