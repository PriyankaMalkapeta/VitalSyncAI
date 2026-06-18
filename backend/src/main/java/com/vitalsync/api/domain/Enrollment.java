package com.vitalsync.api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String planType;
    private LocalDate enrollmentDate;
    @Enumerated(EnumType.STRING)
    private CoverageStatus coverageStatus;

    protected Enrollment() {}

    public Enrollment(String memberId, String planType, LocalDate enrollmentDate, CoverageStatus coverageStatus) {
        this.memberId = memberId;
        this.planType = planType;
        this.enrollmentDate = enrollmentDate;
        this.coverageStatus = coverageStatus;
    }

    public Long getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getPlanType() { return planType; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public CoverageStatus getCoverageStatus() { return coverageStatus; }
}
