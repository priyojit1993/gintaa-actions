package com.asconsoft.gintaa.actions.api;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.common.payload.ApiResponse;
import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.model.ActionFrequency;
import com.asconsoft.gintaa.actions.payload.ActionFrequencyRequest;
import com.asconsoft.gintaa.actions.service.ActionFrequencyService;
import com.asconsoft.gintaa.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/mdr/action/frequency")
@Slf4j
public class ActionFrequencyController {

    @Autowired
    ActionFrequencyService actionFrequencyService;

    @GetMapping
    @ApiOperation(value = "Use this api to get the the all the actionFrequency"
            , response = ApiResponse.class, nickname = "get-all-action-frequency")
    public ResponseEntity<ApiResponse> getAllActionMode() {
        try {
            List<ActionFrequency> allActionFrequency = actionFrequencyService.getAllActionFrequency();
            if (allActionFrequency.isEmpty()) {
                return new ResponseEntity(ApiResponse.ofFailure(HttpStatus.OK.value(),
                        "no action frequency found"), HttpStatus.OK);
            }
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), allActionFrequency,
                    "Action frequencies fetched successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error wile fetching Action frequencies", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action frequency"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = {"/{actionFrequencyName}"})
    @ApiOperation(value = "Use this api to get the the particular action frequency"
            , response = ApiResponse.class, nickname = "get-action-frequency")
    public ResponseEntity<ApiResponse> getActionGroup(@PathVariable String actionFrequencyName) {

        try {
            ActionFrequency actionFrequency = actionFrequencyService.getById(actionFrequencyName)
                    .orElseThrow(() -> new GintaaException("Action frequency not found .."));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionFrequency,
                    "Action Frequency found"));

        } catch (GintaaMdrException e) {
            log.error("Error while fetching action frequency ", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action frequency"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    @ApiOperation(value = "Use this api create/add an action frequency"
            , response = ApiResponse.class, nickname = "add-action-mode")
    public ResponseEntity<ApiResponse> insert(@RequestBody ActionFrequencyRequest actionFrequencyRequest) {
        try {
            ActionFrequency actionFrequency = actionFrequencyService.insertActionFrequency(actionFrequencyRequest);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionFrequency.getFrequencyValue(),
                    "New Action Frequency created successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error creating Action Frequency", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while creating action frequency"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = {"/{actionFrequencyName}"})
    @PostMapping
    @ApiOperation(value = "Use this api delete an action frequency"
            , response = ApiResponse.class, nickname = "delete-action-frequency")

    public ResponseEntity<ApiResponse> delete(@PathVariable String actionFrequencyName) {
        actionFrequencyService.delete(actionFrequencyName);
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(),
                " Action Frequency deleted successfully"));
    }

    @GetMapping("/import/csv")
    @ApiOperation(value = "Use this api import action frequency from excell file"
            , response = ApiResponse.class, nickname = "import-action-frequency")
    public ResponseEntity<ApiResponse> batchImportActionGroup(@RequestBody MultipartFile file) {
        try {
            List<String> actionFrequency = actionFrequencyService.batchImportActionFrequency(FileUtils.multipartToFile(file));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionFrequency,
                    "Action frequencies imported successfully"));
        } catch (IOException e) {
            return new ResponseEntity<>(ApiResponse.ofFailure("Error while importing action frequencies " + e)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
