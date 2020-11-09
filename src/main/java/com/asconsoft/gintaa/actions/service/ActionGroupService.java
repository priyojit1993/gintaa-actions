package com.asconsoft.gintaa.actions.service;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.actions.mapper.MDRMapper;
import com.asconsoft.gintaa.actions.model.ActionGroup;
import com.asconsoft.gintaa.actions.payload.ActionGroupFileModel;
import com.asconsoft.gintaa.actions.payload.ActionGroupRequest;
import com.asconsoft.gintaa.actions.payload.ActionGroupResponse;
import com.asconsoft.gintaa.actions.repository.ActionGroupRepository;
import com.asconsoft.gintaa.actions.validator.GintaaActionValidator;
import com.asconsoft.gintaa.utils.excel.GintaaExcelReader;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ActionGroupService {

    @Autowired
    ActionGroupRepository actionGroupRepository;

    @Autowired
    GintaaActionValidator gintaaActionValidator;

    @Transactional
    public ActionGroupResponse addActionGroup(ActionGroupRequest actionGroupRequest) throws GintaaException {
        gintaaActionValidator.validateActionGroup(actionGroupRequest);
        ActionGroup actionGroup = MDRMapper.INSTANCE.map(actionGroupRequest);
        return MDRMapper.INSTANCE.map(actionGroupRepository.save(actionGroup));
    }

    @Transactional
    public void deleteActionGroup(String actionGroupId) throws GintaaException{
        actionGroupRepository.deleteById(actionGroupId);
    }

    public ActionGroupResponse getById(String actionGroupId) throws GintaaException {
        ActionGroup actionGroup = actionGroupRepository.findById(actionGroupId).orElseThrow(() -> new GintaaException(
                "Action group not found.."
        ));
        return MDRMapper.INSTANCE.map(actionGroup);
    }

    @Transactional
    public List<ActionGroupResponse> getAllActionGroup() throws GintaaException {
        return actionGroupRepository.findAll().stream().map(actionGroup ->
                MDRMapper.INSTANCE.map(actionGroup)).collect(Collectors.toList());
    }

    public List<ActionGroupResponse> batchImportActionGroup(File file) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(file) && file.exists(),
                "File is either null or does not exist");
        List<ActionGroupFileModel> actionGroups = GintaaExcelReader.read(file, ActionGroupFileModel.class, true);
        List<ActionGroupResponse> actionGroupIds = new ArrayList<>();
        actionGroups.stream().forEach(actionGroup -> {
            ActionGroupRequest actionGroupRequest = MDRMapper.INSTANCE.map(actionGroup);
            actionGroupIds.add(addActionGroup(actionGroupRequest));
        });
        return actionGroupIds;

    }


}
