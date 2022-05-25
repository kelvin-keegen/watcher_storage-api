package com.appsbykeegan.watcher_storageapi.entity.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Kelvin Keegan on 5/19/2022
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(

        name = "files_table",
        indexes = {

                @Index(name = "fileNameIndex",columnList = "fileName"),
                @Index(name = "dateAddedIndex",columnList = "dateAdded"),
                @Index(name = "driveLetterIndex",columnList = "driveLetter")
        }
)
public class FileAttributes {

    @SequenceGenerator(

            name = "files_sequence",
            sequenceName = "files_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(

            strategy = GenerationType.SEQUENCE,
            generator = "files_sequence"
    )
    private Long id;
    private String fileName;
    private String dateAdded;
    private String lastModified;
    private String driveLetter;
    private String driveLetterName;


    public FileAttributes(String fileName, String dateAdded, String lastModified, String driveLetter, String driveLetterName) {
        this.fileName = fileName;
        this.dateAdded = dateAdded;
        this.lastModified = lastModified;
        this.driveLetter = driveLetter;
        this.driveLetterName = driveLetterName;
    }
}
