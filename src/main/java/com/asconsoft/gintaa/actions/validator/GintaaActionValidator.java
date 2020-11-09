package com.asconsoft.gintaa.actions.validator;

import com.asconsoft.gintaa.common.exception.GintaaException;
import com.asconsoft.gintaa.actions.payload.ActionGroupRequest;
import edu.stanford.nlp.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GintaaActionValidator {

    public void validateActionGroup(ActionGroupRequest actionGroupRequest) {
        if (checkNullOrEmpty(actionGroupRequest.getDescription()) ||
                checkNullOrEmpty(actionGroupRequest.getName())) {
            throw new GintaaException("Action Group name and/or description cannot" +
                    "be null or empty");
        }
    }


    private boolean checkNullOrEmpty(String s) {
        return StringUtils.isNullOrEmpty(s);
    }

}
