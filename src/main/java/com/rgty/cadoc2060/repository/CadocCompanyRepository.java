package com.rgty.cadoc2060.repository;

import com.rgty.cadoc2060.domain.CadocCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadocCompanyRepository extends JpaRepository<CadocCompany, Long> {
    Optional<CadocCompany> findByCompanyName(String companyName);
}
