package com.asconsoft.gintaa.actions.repository;

import com.asconsoft.gintaa.actions.model.ActionFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionFrequencyRepository extends JpaRepository<ActionFrequency, String> {
}
