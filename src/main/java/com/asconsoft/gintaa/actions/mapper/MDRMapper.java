package com.asconsoft.gintaa.actions.mapper;


import com.asconsoft.gintaa.actions.model.ActionFrequency;
import com.asconsoft.gintaa.actions.model.ActionGroup;
import com.asconsoft.gintaa.actions.model.ActionMode;
import com.asconsoft.gintaa.actions.model.Actions;
import com.asconsoft.gintaa.actions.payload.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface MDRMapper {
    public static MDRMapper INSTANCE = getMapper(MDRMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    public ActionGroup map(ActionGroupRequest actionGroupRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    public ActionMode map(ActionModeRequest actionGroupRequest);

    @Mapping(source = "frequencyValue", target = "frequencyValue")
    public ActionFrequency map(ActionFrequencyRequest actionFrequencyRequest);

    @Mapping(source = "actionLabel", target = "actionLabel")
    @Mapping(source = "actionUrl", target = "actionURL")
    @Mapping(source = "actionPurpose", target = "actionPurpose")
    @Mapping(source = "price", target = "price")
    @Mapping(target = "actionMode", ignore = true)
    @Mapping(target = "actionGroup", ignore = true)
    public Actions map(ActionRequest actionsRequest);


    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    public ActionGroupRequest map(ActionGroupFileModel actionGroupFileModel);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    public ActionModeRequest map(ActionModeFileModel actionModeFileModel);

    @Mapping(source = "frequencyValue", target = "frequencyValue")
    public ActionFrequencyRequest map(ActionFrequencyFileModel actionFrequencyFileModel);


    @Mapping(source = "actionLabel", target = "actionLabel")
    @Mapping(source = "actionUrl", target = "actionUrl")
    @Mapping(source = "actionPurpose", target = "actionPurpose")
    @Mapping(source = "actionGroup", target = "actionGroup")
    @Mapping(source = "actionMode", target = "actionMode")
    public ActionRequest map(ActionsFileModel actionsFileModel);


    //Response mapping

    @Mapping(source = "name", target = "actionGroupName")
    @Mapping(source = "description", target = "actionGroupDescription")
    public ActionGroupResponse map(ActionGroup actionGroup);


    @Mapping(source = "name", target = "actionModeName")
    @Mapping(source = "description", target = "actionModeDescription")
    public ActionsModeResponse map(ActionMode actionMode);


}
