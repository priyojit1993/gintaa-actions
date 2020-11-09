package com.asconsoft.gintaa.actions.repository;

import com.asconsoft.gintaa.actions.model.Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, String> {
}
