package com.asconsoft.gintaa.actions.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "action_mode", schema = "gintaa_action", catalog = "gintaa_action")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionMode {

    @Id
    private String name;
    private String description;


}
