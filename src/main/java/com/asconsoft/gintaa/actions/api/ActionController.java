package com.asconsoft.gintaa.actions.api;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.common.payload.ApiResponse;
import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.model.Actions;
import com.asconsoft.gintaa.actions.payload.ActionRequest;
import com.asconsoft.gintaa.actions.service.ActionsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/mdr/action")
@Slf4j
public class ActionController {

    @Autowired
    ActionsService actionsService;


    @GetMapping
    @ApiOperation(value = "Use this api to get all the actions", nickname = "get-all-actions"
            , response = ApiResponse.class)
    public ResponseEntity<ApiResponse> getAllActions() {
        try {
            List<Actions> alActions = actionsService.getAlActions();
            if (alActions.isEmpty()) {
                return new ResponseEntity(ApiResponse.ofFailure(HttpStatus.OK.value(),
                        "no actions found"), HttpStatus.OK);
            }
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), alActions,
                    "Actions fetched successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error wile fetching Actions", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching actions"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/{actionId}"})
    @ApiOperation(value = "Use this api to get the the particular action"
            , response = ApiResponse.class, nickname = "get-action")
    public ResponseEntity<ApiResponse> getActionGroup(@PathVariable String actionId) {

        try {
            Actions actions = actionsService.findActionsById(actionId)
                    .orElseThrow(() -> new GintaaException("Actions not found .."));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actions,
                    "Actions found"));

        } catch (GintaaMdrException e) {
            log.error("Error while fetching actions ", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching actions"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    @ApiOperation(value = "Use this api create/add an action"
            , response = ApiResponse.class, nickname = "add-action")
    public ResponseEntity<ApiResponse> insert(@RequestBody ActionRequest actionsRequest) {
        try {
            Actions actions = actionsService.addActions(actionsRequest);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actions.getActionId(),
                    "New Action created successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error while creating action", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while creating action"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{actionId}"})
    @PostMapping
    @ApiOperation(value = "Use this api delete an action"
            , response = ApiResponse.class, nickname = "delete-action")

    public ResponseEntity<ApiResponse> delete(@PathVariable String actionId) {
        try {
            actionsService.deleteActions(actionId);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(),
                    " Action Frequency deleted successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error while deleting action", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while deleting action"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }





}
