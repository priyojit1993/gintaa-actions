package com.asconsoft.gintaa.actions.service;

import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.mapper.MDRMapper;
import com.asconsoft.gintaa.actions.model.ActionMode;
import com.asconsoft.gintaa.actions.payload.ActionModeFileModel;
import com.asconsoft.gintaa.actions.payload.ActionModeRequest;
import com.asconsoft.gintaa.actions.repository.ActionModeRepository;
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
public class ActionModeService {

    @Autowired
    private ActionModeRepository actionModeRepository;


    @Transactional
    public ActionMode insertActionMode(ActionModeRequest actionModeRequest) throws GintaaMdrException {
        ActionMode actionMode = MDRMapper.INSTANCE.map(actionModeRequest);
        return actionModeRepository.save(actionMode);
    }

    @Transactional
    public void deleteActionMode(String id) {
        actionModeRepository.deleteById(id);
    }

    @Transactional
    public Optional<ActionMode> findActionModeById(String id) throws GintaaMdrException {
        return actionModeRepository.findById(id);
    }

    @Transactional
    public List<ActionMode> getAllActionModes() throws GintaaMdrException {
        return actionModeRepository.findAll();
    }

    public List<String> batchImportActionMode(File file) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(file) && file.exists(),
                "File is either null or does not exist");
        List<ActionModeFileModel> actionModes = GintaaExcelReader.read(file, ActionModeFileModel.class, true);
        List<String> actionModeIds = new ArrayList<>();
        actionModes.stream().forEach(actionMode -> {
            ActionModeRequest actionModeRequest = MDRMapper.INSTANCE.map(actionMode);
            try {
                actionModeIds.add(insertActionMode(actionModeRequest).getName());
            } catch (GintaaMdrException e) {
                log.error(e.toString());
            }
        });
        return actionModeIds;

    }


}
