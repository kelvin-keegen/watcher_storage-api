package com.appsbykeegan.watcher_storageapi.service;

import com.appsbykeegan.watcher_storageapi.entity.models.ApiResponseBody;
import com.appsbykeegan.watcher_storageapi.entity.models.DeleteBodyModel;
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
                    fileAttributeModel.getDriveLetterName()
            );

        } else {

            log.error("Data transmitted might have an unexpected attribute, examine data: {}",fileAttributeModel);
            return new ApiResponseBody(400,"An error is present from the data received",null);
        }

        // Business logic adding data to database

        try {

            Optional<FileAttributes> optionalFileAttributes = appRepository.findByFileName(newReport.getFileName());

            if (optionalFileAttributes.isPresent()) {

                log.warn("Report data already exists");
                return new ApiResponseBody(500,"Report data already exists!",null);
            }

            appRepository.save(newReport);
            return new ApiResponseBody(200,"File report added.",null);

        } catch (Exception exception) {

            log.error("Something went wrong, exception message: {}",exception.getMessage());
            return new ApiResponseBody(500, "Something went wrong!", null);

        }

    }

    public ApiResponseBody DeleteFileReport(DeleteBodyModel deleteRequestBody) {

        // Data analysis

        if (deleteRequestBody.getFileName().isEmpty() || deleteRequestBody.getDriverLetter().isEmpty()) {

            // add other necessary data checks

            log.error("Data transmitted might have an unexpected attribute, examine data: {}",deleteRequestBody);
            return new ApiResponseBody(400,"An error is present from the data received",null);
        }

        // Business logic for deleting entry from database

        try {

            Optional<FileAttributes> optionalFileAttributes = appRepository.findByFileName(deleteRequestBody.getFileName());

            if (optionalFileAttributes.isEmpty()) {

                log.warn("No such file found, fileName: {}",deleteRequestBody.getFileName());
                return new ApiResponseBody(404,"No such file found.",null);
            }

            System.out.println(deleteRequestBody.getDriverLetter());
            System.out.println(deleteRequestBody.getUsbDriveName());

            if (optionalFileAttributes.get().getDriveLetter().equals(deleteRequestBody.getDriverLetter()) && optionalFileAttributes
                    .get().getDriveLetterName().equals(deleteRequestBody.getUsbDriveName())) {

                appRepository.deleteById(optionalFileAttributes.get().getId());
                return new ApiResponseBody(200,"Entry successfully deleted.",null);
            }

        } catch (Exception exception) {

            log.error("An error encountered, exception message: {}", exception.getMessage());
            return new ApiResponseBody(500, "Something went wrong!", null);
        }

        log.error("Unhandled exception case found, If statement check was not conducted.");
        return new ApiResponseBody(500,"Unhandled exception case found.",null);
    }
}
