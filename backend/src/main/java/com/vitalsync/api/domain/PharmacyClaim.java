package com.vitalsync.api.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pharmacy_claims")
public class PharmacyClaim {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String drugName;
    private String drugCategory;
    @Column(precision = 12, scale = 2)
    private BigDecimal claimCost;
    private LocalDate fillDate;

    protected PharmacyClaim() {}

    public PharmacyClaim(String memberId, String drugName, String drugCategory, BigDecimal claimCost, LocalDate fillDate) {
        this.memberId = memberId;
        this.drugName = drugName;
        this.drugCategory = drugCategory;
        this.claimCost = claimCost;
        this.fillDate = fillDate;
    }

    public Long getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getDrugName() { return drugName; }
    public String getDrugCategory() { return drugCategory; }
    public BigDecimal getClaimCost() { return claimCost; }
    public LocalDate getFillDate() { return fillDate; }
}
