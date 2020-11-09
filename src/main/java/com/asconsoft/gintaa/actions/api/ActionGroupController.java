package com.asconsoft.gintaa.actions.api;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.common.payload.ApiResponse;
import com.asconsoft.gintaa.actions.payload.ActionGroupRequest;
import com.asconsoft.gintaa.actions.payload.ActionGroupResponse;
import com.asconsoft.gintaa.actions.service.ActionGroupService;
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
@RequestMapping(value = "/v1/mdr/action/group")
@Slf4j
public class ActionGroupController {

    @Autowired
    private ActionGroupService actionGroupService;

    @GetMapping
    @ApiOperation(value = "Use this api to get the the all the actionGroup"
            , response = ApiResponse.class, nickname = "get-all-action-group")
    public ResponseEntity<ApiResponse> getAllActionGroup() {
        List<ActionGroupResponse> allActionGroup = null;
        try {
            allActionGroup = actionGroupService.getAllActionGroup();
            if (allActionGroup.isEmpty()) {
                return new ResponseEntity(ApiResponse.ofFailure(HttpStatus.OK.value(),
                        "no action groups found"), HttpStatus.OK);
            }
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), allActionGroup,
                    "Action Groups fetched successfully"));
        } catch (GintaaException e) {
            log.error("Error wile fetching Action Groups", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action groups"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = {"/{actionGroupName}"})
    @ApiOperation(value = "Use this api to get the the particular  actionGroup"
            , response = ApiResponse.class, nickname = "get-action-group")
    public ResponseEntity<ApiResponse> getActionGroup(@PathVariable String actionGroupName) {

        try {
            ActionGroupResponse actionGroup = actionGroupService.getById(actionGroupName);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionGroup, "Action group found"));

        } catch (GintaaException e) {
            log.error("Error while fetching action group ", e);
            return new ResponseEntity(ApiResponse.ofFailure("error while fetching action groups"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Use this api create/add an action group"
            , response = ApiResponse.class, nickname = "add-action-group")
    public ResponseEntity<ApiResponse> insert(@RequestBody ActionGroupRequest actionGroupRequest) {
        try {
            ActionGroupResponse actionGroup = actionGroupService.addActionGroup(actionGroupRequest);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionGroup,
                    "New Action Group created successfully"));
        } catch (Exception e) {
            log.error("Failed to add Action group due to ", e);
            return new ResponseEntity<>(ApiResponse.ofFailure("Error while Adding action group " + e)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{actionGroupName}"})
    @ApiOperation(value = "Use this api delete an action group"
            , response = ApiResponse.class, nickname = "delete-action-group")
    public ResponseEntity<ApiResponse> delete(@PathVariable String actionGroupName) {
        try {
            actionGroupService.deleteActionGroup(actionGroupName);
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(),
                    "New Action Group deleted successfully"));
        } catch (Exception e) {
            log.error("Could not delete action group due to ", e);
            return new ResponseEntity<>(ApiResponse.ofFailure("Error while deleting action group " + e)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import/csv")
    @ApiOperation(value = "Use this api import action groups from excell file"
            , response = ApiResponse.class, nickname = "import-action-groups")
    public ResponseEntity<ApiResponse> batchImportActionGroup(@RequestBody MultipartFile file) {
        try {
            List<ActionGroupResponse> actionGroup = actionGroupService.batchImportActionGroup(FileUtils.multipartToFile(file));
            return ResponseEntity.ok(ApiResponse.ofSuccess(HttpStatus.OK.value(), actionGroup,
                    "Action groups imported successfully"));
        } catch (IOException e) {
            return new ResponseEntity<>(ApiResponse.ofFailure("Error while importing action group " + e)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
