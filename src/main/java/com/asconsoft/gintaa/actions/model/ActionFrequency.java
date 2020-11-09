package com.asconsoft.gintaa.actions.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "action_frequency", schema = "gintaa_action", catalog = "gintaa_action")
public class ActionFrequency {
    @Id
    @Column(name = "frequency_value")
    private String frequencyValue;
}
