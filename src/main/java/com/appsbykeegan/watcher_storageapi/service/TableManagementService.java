package com.appsbykeegan.watcher_storageapi.service;

import com.appsbykeegan.watcher_storageapi.entity.models.ApiResponseBody;
import com.appsbykeegan.watcher_storageapi.entity.models.FileAttributeModel;
import com.appsbykeegan.watcher_storageapi.entity.table.FileAttributes;
import com.appsbykeegan.watcher_storageapi.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Kelvin Keegan on 5/24/2022
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TableManagementService {

    private final AppRepository appRepository;

    public ApiResponseBody RetrieveAllContents() {

        if (appRepository.count() == 0) {

            return new ApiResponseBody(
                    500,
                    "No data found",
                    null
            );
        }

        return new ApiResponseBody(
                200,
                "data retrieved successfully",
                appRepository.findAll(Sort.by(Sort.Direction.DESC,"dateAdded")));
    }

    public ApiResponseBody PageableRetrieveAll(PageRequest pageRequest) {

        if (appRepository.count() == 0) {

            return new ApiResponseBody(
                    500,
                    "No data found",
                    null
            );
        }

        return new ApiResponseBody(
                200,
                "data retrieved",
                appRepository.findAll(pageRequest).getContent()
        );

    }

    public ApiResponseBody AddFileReport(FileAttributeModel fileAttributeModel) {

        // Data analysis checking if data is valid.

        FileAttributes newReport = new FileAttributes();

        if ((fileAttributeModel.getDriveLetter().length() == 1) && (Character.isUpperCase(fileAttributeModel.getDriveLetter().charAt(0)))) {

            newReport = new FileAttributes(

                    fileAttributeModel.getFileName(),
                    fileAttributeModel.getDateAdded(),
                    fileAttributeModel.getLastModified(),
                    fileAttributeModel.getDriveLetter(),
                    fileAttributeModel.getFileStatus()
            );

        } else {

            return new ApiResponseBody(400,"An error is present from the data received",null);
        }

        // Business logic adding data to database

        try {

            Optional<FileAttributes> optionalFileAttributes = appRepository.findByFileName(newReport.getFileName());

            if (optionalFileAttributes.isPresent()) {

                return new ApiResponseBody(500,"Report data already exists",null);
            }

            appRepository.save(newReport);
            return new ApiResponseBody(200,"File report added",null);

        } catch (Exception exception) {

            log.error("Something went wrong, exception message: {}",exception.getMessage());
            return new ApiResponseBody(500, "Something went wrong!", null);

        }

    }


    public ApiResponseBody DeleteFileReport(String fileName, String localDateTimeAdded, String driveLetter) {

        // Data analysis

        if (fileName.isEmpty() || driveLetter.isEmpty()) {

            // add other necessary data checks

            return new ApiResponseBody(400,"An error is present from the data received",null);
        }

        // Business logic for deleting entry from database

        try {

            Optional<FileAttributes> optionalFileAttributes = appRepository.findByFileName(fileName);

            if (optionalFileAttributes.isEmpty()) {

                return new ApiResponseBody(500,"No such file found ",null);
            }

            if (optionalFileAttributes.get().getDateAdded().equals(localDateTimeAdded) && optionalFileAttributes
                    .get().getDriveLetter().equals(driveLetter)) {

                appRepository.delete(optionalFileAttributes.get());
                return new ApiResponseBody(200,"Entry successfully deleted",null);
            }

        } catch (Exception exception) {

            log.error("An error encountered, exception message: {}", exception.getMessage());
            return new ApiResponseBody(500, "Something went wrong!", null);
        }

        return new ApiResponseBody(500,"Unhandled exception case found",null);
    }
}
