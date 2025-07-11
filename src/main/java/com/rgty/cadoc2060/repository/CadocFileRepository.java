package com.rgty.cadoc2060.repository;

import com.rgty.cadoc2060.domain.CadocFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadocFileRepository extends JpaRepository<CadocFile, Long> {
}
