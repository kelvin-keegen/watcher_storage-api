package com.appsbykeegan.watcher_storageapi.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kelvin Keegan on 5/19/2022
 */

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AppController {

    @GetMapping(path = "/health-status")
    public String HealthStatus() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("health","Good");
        jsonObject.put("statusCode",200);
        jsonObject.put("message","Server health is Good");

        return jsonObject.toJSONString();
    }

}
