package com.asconsoft.gintaa.actions.service;

import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.mapper.MDRMapper;
import com.asconsoft.gintaa.actions.model.ActionMode;
import com.asconsoft.gintaa.actions.payload.ActionModeFileModel;
import com.asconsoft.gintaa.actions.payload.ActionModeRequest;
import com.asconsoft.gintaa.actions.payload.ActionsModeResponse;
import com.asconsoft.gintaa.actions.repository.ActionModeRepository;
import com.asconsoft.gintaa.actions.validator.GintaaActionValidator;
import com.asconsoft.gintaa.common.exception.GintaaException;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActionModeService {

    @Autowired
    private ActionModeRepository actionModeRepository;

    @Autowired
    GintaaActionValidator gintaaActionValidator;


    @Transactional
    public ActionsModeResponse insertActionMode(ActionModeRequest actionModeRequest) throws GintaaException {
        gintaaActionValidator.validateActionMode(actionModeRequest);
        ActionMode actionMode = MDRMapper.INSTANCE.map(actionModeRequest);
        return MDRMapper.INSTANCE.map(actionModeRepository.save(actionMode));
    }

    @Transactional
    public void deleteActionMode(String id) throws GintaaException {
        actionModeRepository.deleteById(id);
    }

    @Transactional
    public ActionsModeResponse findActionModeById(String id) throws GintaaMdrException {
        ActionMode actionMode = actionModeRepository.findById(id).orElseThrow(() -> new GintaaException("Action mode not found"));
        return MDRMapper.INSTANCE.map(actionMode);
    }

    @Transactional
    public List<ActionsModeResponse> getAllActionModes() throws GintaaMdrException {
        return actionModeRepository.findAll()
                .stream().map(actionMode -> MDRMapper.INSTANCE.map(actionMode)).collect(Collectors.toList());
    }

    public List<ActionsModeResponse> batchImportActionMode(File file) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(file) && file.exists(),
                "File is either null or does not exist");
        List<ActionModeFileModel> actionModes = GintaaExcelReader.read(file, ActionModeFileModel.class, true);
        List<ActionsModeResponse> actionsModeResponses = new ArrayList<>();
        actionModes.stream().forEach(actionMode -> {
            ActionModeRequest actionModeRequest = MDRMapper.INSTANCE.map(actionMode);
            try {
                actionsModeResponses.add(insertActionMode(actionModeRequest));
            } catch (GintaaMdrException e) {
                log.error(e.toString());
            }
        });
        return actionsModeResponses;

    }


}
