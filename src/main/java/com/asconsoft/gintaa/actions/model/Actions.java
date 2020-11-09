package com.asconsoft.gintaa.actions.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "actions", schema = "gintaa_action", catalog = "gintaa_action")
public class Actions {


    @Id
    @Column(name = "action_id")
    private String actionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_mode", referencedColumnName = "name")
    private ActionMode actionMode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_group", referencedColumnName = "name")
    private ActionGroup actionGroup;

    @Column(name = "action_label")
    private String actionLabel;

    @Column(name = "action_purpose")
    private String actionPurpose;

    @Column(name = "action_url")
    private String actionURL;

    private double price;

}
