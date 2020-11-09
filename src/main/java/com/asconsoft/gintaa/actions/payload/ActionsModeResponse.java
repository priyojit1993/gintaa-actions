package com.asconsoft.gintaa.actions.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionsModeResponse {
    private String actionModeName;
    private String actionModeDescription;
}
