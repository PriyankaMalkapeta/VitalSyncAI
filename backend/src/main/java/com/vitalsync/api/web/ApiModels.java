package com.vitalsync.api.web;

import com.vitalsync.api.domain.CoverageStatus;
import com.vitalsync.api.domain.RiskCategory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public final class ApiModels {
    private ApiModels() {}

    public record DashboardSummary(
            long totalMembers,
            long activeEnrollments,
            double activeEnrollmentRate,
            BigDecimal totalPharmacySpend,
            BigDecimal averageCostPerMember,
            List<PlanSpend> spendByPlan,
            List<DimensionSpend> spendByState,
            List<DimensionSpend> topDrugs,
            Instant asOf) {}

    public record PlanSpend(String plan, BigDecimal spend, double share) {}
    public record DimensionSpend(String name, BigDecimal spend) {}
    public record MemberSummary(String memberId, int age, String gender, String state, RiskCategory riskCategory,
                                String planType, CoverageStatus coverageStatus, BigDecimal pharmacySpend) {}
    public record Member360(String memberId, int age, String gender, String state, RiskCategory riskCategory,
                            EnrollmentView enrollment, List<ClaimView> pharmacyClaims, BigDecimal totalPharmacySpend,
                            Map<String, String> lineage) {}
    public record EnrollmentView(String planType, LocalDate enrollmentDate, CoverageStatus coverageStatus) {}
    public record ClaimView(String drugName, String drugCategory, BigDecimal claimCost, LocalDate fillDate) {}
}
