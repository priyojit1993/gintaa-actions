package com.asconsoft.gintaa.actions.service;

import com.asconsoft.gintaa.actions.exception.GintaaMdrException;
import com.asconsoft.gintaa.actions.mapper.MDRMapper;
import com.asconsoft.gintaa.actions.model.ActionFrequency;
import com.asconsoft.gintaa.actions.payload.ActionFrequencyFileModel;
import com.asconsoft.gintaa.actions.payload.ActionFrequencyRequest;
import com.asconsoft.gintaa.actions.repository.ActionFrequencyRepository;
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
public class ActionFrequencyService {
    @Autowired
    ActionFrequencyRepository actionFrequencyRepository;


    @Transactional
    public ActionFrequency insertActionFrequency(ActionFrequencyRequest actionFrequencyRequest) throws GintaaMdrException {
        ActionFrequency actionFrequency = MDRMapper.INSTANCE.map(actionFrequencyRequest);
        return actionFrequencyRepository.save(actionFrequency);
    }

    @Transactional
    public Optional<ActionFrequency> getById(String id) throws GintaaMdrException {
        return actionFrequencyRepository.findById(id);
    }

    @Transactional
    public void delete(String id) {
        actionFrequencyRepository.deleteById(id);
    }

    @Transactional
    public List<ActionFrequency> getAllActionFrequency() throws GintaaMdrException {
        return actionFrequencyRepository.findAll();
    }


    public List<String> batchImportActionFrequency(File file) throws IOException {
        Preconditions.checkArgument(Objects.nonNull(file) && file.exists(),
                "File is either null or does not exist");
        List<ActionFrequencyFileModel> frequencyFileModels = GintaaExcelReader.read(file, ActionFrequencyFileModel.class,
                true);
        List<String> frequencyIds = new ArrayList<>();
        frequencyFileModels.stream().forEach(frequencyModel -> {
            ActionFrequencyRequest frequencyRequest = MDRMapper.INSTANCE.map(frequencyModel);
            try {
                frequencyIds.add(insertActionFrequency(frequencyRequest).getFrequencyValue());
            } catch (GintaaMdrException e) {
                log.error(e.toString());
            }
        });
        return frequencyIds;

    }
}
