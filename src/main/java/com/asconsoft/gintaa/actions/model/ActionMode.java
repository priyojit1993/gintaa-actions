package com.asconsoft.gintaa.actions.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column(name = "ct")
    private Timestamp creationDate;
    @Column(name = "lu")
    private Timestamp updationDate;

    @PrePersist
    public void beforePersist() {
        creationDate = updationDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void beforeUpdate() {
        creationDate = updationDate = new Timestamp(System.currentTimeMillis());
    }


}
