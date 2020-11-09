package com.asconsoft.gintaa.actions.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionModeRequest {
    private String name;
    private String description;
}
