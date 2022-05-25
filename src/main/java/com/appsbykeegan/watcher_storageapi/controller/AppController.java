package com.appsbykeegan.watcher_storageapi.controller;

import com.appsbykeegan.watcher_storageapi.entity.models.ApiResponseBody;
import com.appsbykeegan.watcher_storageapi.entity.models.FileAttributeModel;
import com.appsbykeegan.watcher_storageapi.service.TableManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kelvin Keegan on 5/19/2022
 */

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppController {

    private final TableManagementService tableManagementService;

    @GetMapping(path = "/health-status")
    private String HealthStatus() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode",200);
        jsonObject.put("message","Server health is Good");
        jsonObject.put("health","Good");

        return jsonObject.toJSONString();
    }

    @GetMapping(path = "/get-all")
    private ApiResponseBody Retrieve_All_Database_Data() {

        return tableManagementService.RetrieveAllContents();
    }

    @PostMapping(path = "/get-all-pageable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Data from the database was successfully retrieved",content = @Content),
            @ApiResponse(responseCode = "500",description = "An error encountered during data retrieval",content = @Content)
    })
    @Operation(
            summary = "Data retrieval endpoint",
            parameters = {
            @Parameter(name = "pageNumber",description = "Input requests a specific page number."),
            @Parameter(name = "pageSize",description = "Input here defines a specific number of records per page requested.")},
            description = "This endpoint is a pageable endpoint for controlled data retrieval. See the params for a detailed scope."

    )
    private ApiResponseBody Pageable_Retrieval(@RequestParam int pageNumber, int pageSize) {

        return tableManagementService.PageableRetrieveAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC,"dateAdded")));
    }

    @PostMapping(path = "/add-file_report")
    private ApiResponseBody Adding_ReportData(@RequestBody FileAttributeModel fileAttributeModel) {

        return tableManagementService.AddFileReport(fileAttributeModel);
    }

    @PostMapping(path = "/delete-file_report")
    private ApiResponseBody Deleting_ReportData(@RequestParam String fileName, String localDateTimeAdded, String driveLetter) {

        return tableManagementService.DeleteFileReport(fileName, localDateTimeAdded, driveLetter);
    }

}
