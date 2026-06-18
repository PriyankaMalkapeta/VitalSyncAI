package com.vitalsync.api.service;

import com.vitalsync.api.domain.*;
import com.vitalsync.api.repository.*;
import com.vitalsync.api.web.ApiModels.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HealthcareAnalyticsService {
    private final MemberRepository members;
    private final EnrollmentRepository enrollments;
    private final PharmacyClaimRepository claims;

    public HealthcareAnalyticsService(MemberRepository members, EnrollmentRepository enrollments,
                                      PharmacyClaimRepository claims) {
        this.members = members;
        this.enrollments = enrollments;
        this.claims = claims;
    }

    public DashboardSummary dashboard() {
        List<Member> allMembers = members.findAll();
        List<Enrollment> allEnrollments = enrollments.findAll();
        List<PharmacyClaim> allClaims = claims.findAll();
        long active = allEnrollments.stream().filter(e -> e.getCoverageStatus() == CoverageStatus.ACTIVE).count();
        BigDecimal total = sumClaims(allClaims);

        Map<String, String> memberPlans = allEnrollments.stream()
                .collect(Collectors.toMap(Enrollment::getMemberId, Enrollment::getPlanType, (a, b) -> b));
        Map<String, String> memberStates = allMembers.stream()
                .collect(Collectors.toMap(Member::getId, Member::getState));

        List<PlanSpend> byPlan = groupSpend(allClaims, c -> memberPlans.getOrDefault(c.getMemberId(), "Unknown"))
                .entrySet().stream().sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .map(e -> new PlanSpend(e.getKey(), e.getValue(), percentage(e.getValue(), total))).toList();
        List<DimensionSpend> byState = toDimensionSpend(groupSpend(allClaims,
                c -> memberStates.getOrDefault(c.getMemberId(), "Unknown")));
        List<DimensionSpend> topDrugs = toDimensionSpend(groupSpend(allClaims, PharmacyClaim::getDrugName));

        return new DashboardSummary(allMembers.size(), active,
                allMembers.isEmpty() ? 0 : round(active * 100.0 / allMembers.size()), total,
                allMembers.isEmpty() ? BigDecimal.ZERO : total.divide(BigDecimal.valueOf(allMembers.size()), 2, RoundingMode.HALF_UP),
                byPlan, byState, topDrugs, Instant.now());
    }

    public List<MemberSummary> memberSummaries() {
        return members.findAll().stream().map(member -> {
            Enrollment enrollment = enrollments.findFirstByMemberIdOrderByEnrollmentDateDesc(member.getId()).orElse(null);
            BigDecimal spend = sumClaims(claims.findByMemberIdOrderByFillDateDesc(member.getId()));
            return new MemberSummary(member.getId(), member.getAge(), member.getGender(), member.getState(),
                    member.getRiskCategory(), enrollment == null ? null : enrollment.getPlanType(),
                    enrollment == null ? null : enrollment.getCoverageStatus(), spend);
        }).toList();
    }

    public Member360 member360(String id) {
        Member member = members.findById(id).orElseThrow(() -> new NoSuchElementException("Member not found"));
        Enrollment enrollment = enrollments.findFirstByMemberIdOrderByEnrollmentDateDesc(id).orElse(null);
        List<PharmacyClaim> memberClaims = claims.findByMemberIdOrderByFillDateDesc(id);
        EnrollmentView enrollmentView = enrollment == null ? null : new EnrollmentView(enrollment.getPlanType(),
                enrollment.getEnrollmentDate(), enrollment.getCoverageStatus());
        List<ClaimView> claimViews = memberClaims.stream().map(c -> new ClaimView(c.getDrugName(),
                c.getDrugCategory(), c.getClaimCost(), c.getFillDate())).toList();
        return new Member360(member.getId(), member.getAge(), member.getGender(), member.getState(),
                member.getRiskCategory(), enrollmentView, claimViews, sumClaims(memberClaims),
                Map.of("member", "MEMBER_MANAGEMENT", "enrollment", "ENROLLMENT", "pharmacy", "PBM"));
    }

    private Map<String, BigDecimal> groupSpend(List<PharmacyClaim> data, Function<PharmacyClaim, String> key) {
        return data.stream().collect(Collectors.groupingBy(key,
                Collectors.reducing(BigDecimal.ZERO, PharmacyClaim::getClaimCost, BigDecimal::add)));
    }

    private List<DimensionSpend> toDimensionSpend(Map<String, BigDecimal> grouped) {
        return grouped.entrySet().stream().sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .map(e -> new DimensionSpend(e.getKey(), e.getValue())).toList();
    }

    private BigDecimal sumClaims(List<PharmacyClaim> data) {
        return data.stream().map(PharmacyClaim::getClaimCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private double percentage(BigDecimal value, BigDecimal total) {
        return total.signum() == 0 ? 0 : round(value.multiply(BigDecimal.valueOf(100)).divide(total, 2, RoundingMode.HALF_UP).doubleValue());
    }

    private double round(double value) { return Math.round(value * 10.0) / 10.0; }
}
