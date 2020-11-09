package com.asconsoft.gintaa.actions.service;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.mapper.MDRMapper;
import com.asconsoft.gintaa.actions.model.ActionGroup;
import com.asconsoft.gintaa.actions.model.ActionMode;
import com.asconsoft.gintaa.actions.model.Actions;
import com.asconsoft.gintaa.actions.payload.ActionRequest;
import com.asconsoft.gintaa.actions.payload.ActionsFileModel;
import com.asconsoft.gintaa.actions.repository.ActionGroupRepository;
import com.asconsoft.gintaa.actions.repository.ActionModeRepository;
import com.asconsoft.gintaa.actions.repository.ActionsRepository;
import com.asconsoft.gintaa.utils.excel.GintaaExcelReader;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ActionsService {

    @Autowired
    private ActionGroupRepository actionGroupRepository;

    @Autowired
    private ActionModeRepository actionModeRepository;

    @Autowired
    private ActionsRepository actionsRepository;

    @Transactional
    public Actions addActions(ActionRequest actionsRequest) throws GintaaMdrException {
        Actions actions = MDRMapper.INSTANCE.map(actionsRequest);

        ActionMode actionMode = actionModeRepository.findById(actionsRequest.getActionMode()).orElseThrow(
                () -> new GintaaException("Action Mode not found for given action mode : " + actionsRequest.getActionMode()));


        ActionGroup actionGroup = actionGroupRepository.findById(actionsRequest.getActionGroup()).orElseThrow(
                () -> new GintaaException("Action Group not found for given action mode : " + actionsRequest.getActionMode()));
        actions.setActionGroup(actionGroup);
        actions.setActionMode(actionMode);
        actions.setActionId(actionGroup.getName() + "_" + actionMode.getName());
        return actionsRepository.save(actions);
    }

    @Transactional
    public Optional<Actions> findActionsById(String actionId) throws GintaaMdrException {
        return actionsRepository.findById(actionId);
    }


    @Transactional
    public List<Actions> getAlActions() throws GintaaMdrException {
        return actionsRepository.findAll();
    }

    @Transactional
    public void deleteActions(String actionId) throws GintaaMdrException {
        actionsRepository.deleteById(actionId);
    }


    public List<String> batchImportAction(File file) throws IOException {
        List<String> actionIds = new ArrayList<>();
        Preconditions.checkArgument(Objects.nonNull(file) && file.exists(),
                "File is either null or does not exist");
        List<ActionsFileModel> actionsFileModels = GintaaExcelReader.read(file, ActionsFileModel.class, true);
        actionsFileModels.stream().forEach(actionFileModel -> {
            ActionRequest actionsRequest = MDRMapper.INSTANCE.map(actionFileModel);
            try {
                actionIds.add(addActions(actionsRequest).getActionId());
            } catch (GintaaMdrException e) {
                log.error(e.toString());
            }

        });
        return actionIds;
    }


}
