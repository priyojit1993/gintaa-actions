package com.asconsoft.gintaa.actions.repository;

import com.asconsoft.gintaa.actions.model.ActionMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionModeRepository extends JpaRepository<ActionMode, String> {
}
