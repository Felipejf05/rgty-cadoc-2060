package com.rgty.cadoc2060.repository;

import com.rgty.cadoc2060.domain.CadocStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadocStatusRepository  extends JpaRepository<CadocStatus, Long> {
    Optional<CadocStatus> findByNemotecnico(String nemotecnico);
}
