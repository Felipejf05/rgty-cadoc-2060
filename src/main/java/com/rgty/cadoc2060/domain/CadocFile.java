package com.rgty.cadoc2060.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cadoc2060")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CadocFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accounting_item", length = 10, nullable = false)
    private String accountingItem;

    @Column(name = "risk_factor", length = 10, nullable = false)
    private String riskFactor;

    @Column(nullable = false)
    private Integer vertex;

    @Column(name = "activity_code", length = 10, nullable = false)
    private String activityCode;

    @Column(name = "institution_cnpj", length = 14, nullable = false)
    private String institutionCnpj;

}
