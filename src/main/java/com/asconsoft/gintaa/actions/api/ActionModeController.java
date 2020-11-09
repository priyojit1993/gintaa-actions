package com.asconsoft.gintaa.actions.api;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.common.payload.ApiResponse;
import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.model.ActionMode;
import com.asconsoft.gintaa.actions.payload.ActionModeRequest;
import com.asconsoft.gintaa.actions.service.ActionModeService;
import com.asconsoft.gintaa.security.GintaaUserPrincipal;
import com.asconsoft.gintaa.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/mdr/action/mode")
@Slf4j
public class ActionModeController {
    @Autowired
    private ActionModeService actionModeService;

    @GetMapping
//    @RolesAllowed({"ROLE_ANONYMOUS", "ROLE_ADMIN"})
    @ApiOperation(value = "Use this api to get the the all the actionMode"
            , response = ApiResponse.class, nickname = "get-all-action-mode")
    public ResponseEntity<ApiResponse> getAllActionMode(
            @NonNull @AuthenticationPrincipal GintaaUserPrincipal authenticatedPrincipal
    ) {
        try {
            List<ActionMode> allActionModes = actionModeService.getAllActionModes();
            if (allActionModes.isEmpty()) {
                return new ResponseEntity(ApiResponse.ofFailure(HttpStatus.OK.value(),
                        "no action modes found"), HttpStatus.OK);
            }
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), allActionModes,
                    "Action modes fetched successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error wile fetching Action Modes", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action modes"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/{actionModeName}"})
//    @RolesAllowed({"ROLE_ANNONYMOUS", "ROLE_ADMIN"})
    @ApiOperation(value = "Use this api to get the the particular actionModeName"
            , response = ApiResponse.class, nickname = "get-action-mode")
    public ResponseEntity<ApiResponse> getActionGroup(@PathVariable String actionModeName) {

        try {
            ActionMode actionModeById = actionModeService.findActionModeById(actionModeName)
                    .orElseThrow(() -> new GintaaException("Action group not found .."));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionModeById, "Action mode found"));

        } catch (GintaaMdrException e) {
            log.error("Error while fetching action group ", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action groups"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
//    @RolesAllowed({"ROLE_ANNONYMOUS", "ROLE_ADMIN"})
    @ApiOperation(value = "Use this api create/add an action mode"
            , response = ApiResponse.class, nickname = "add-action-mode")
    public ResponseEntity<ApiResponse> insert(@RequestBody ActionModeRequest actionModeRequest) {
        try {
            ActionMode actionMode = actionModeService.insertActionMode(actionModeRequest);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionMode.getName(),
                    "New Action Mode created successfully"));
        } catch (GintaaMdrException e) {
            log.error("Error while creating Action Modes", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while creating action modes"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{actionModeName}"})
//    @RolesAllowed({"ROLE_ANNONYMOUS", "ROLE_ADMIN"})
    @PostMapping
    @ApiOperation(value = "Use this api delete an action group"
            , response = ApiResponse.class, nickname = "delete-action-group")

    public ResponseEntity<ApiResponse> delete(@PathVariable String actionModeName) {
        actionModeService.deleteActionMode(actionModeName);
        return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(),
                "New Action Mode deleted successfully"));
    }


    @GetMapping("/import/csv")
//    @RolesAllowed({"ROLE_ANNONYMOUS", "ROLE_ADMIN"})
    @ApiOperation(value = "Use this api import action modes from excell file"
            , response = ApiResponse.class, nickname = "import-action-modes")
    public ResponseEntity<ApiResponse> batchImportActionGroup(@RequestBody MultipartFile file) {
        try {
            List<String> actionModes = actionModeService.batchImportActionMode(FileUtils.multipartToFile(file));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionModes,
                    "Action modes imported successfully"));
        } catch (IOException e) {
            return new ResponseEntity<>(ApiResponse.ofFailure("Error while importing action modes " + e)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
