package com.asconsoft.gintaa.actions.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionGroupResponse {
    private String actionGroupName;
    private String actionGroupDescription;
}
