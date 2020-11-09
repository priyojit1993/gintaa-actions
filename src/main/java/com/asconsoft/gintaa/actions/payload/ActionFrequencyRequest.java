package com.asconsoft.gintaa.actions.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionFrequencyRequest {
    private String frequencyValue;
}
