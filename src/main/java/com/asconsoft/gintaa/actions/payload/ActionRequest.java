package com.asconsoft.gintaa.actions.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionRequest {


    private String actionLabel;
    private String actionUrl;
    private String actionPurpose;
    private String actionGroup;
    private String actionMode;
    private double price;


}
