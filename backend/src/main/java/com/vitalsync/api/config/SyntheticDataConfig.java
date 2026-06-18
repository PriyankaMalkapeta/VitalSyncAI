package com.vitalsync.api.config;

import com.vitalsync.api.domain.*;
import com.vitalsync.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class SyntheticDataConfig {
    @Bean
    CommandLineRunner seedSyntheticData(MemberRepository members, EnrollmentRepository enrollments,
                                        PharmacyClaimRepository claims) {
        return args -> {
            members.saveAll(List.of(
                    new Member("Member-1001", 58, "F", "TX", RiskCategory.HIGH),
                    new Member("Member-1002", 44, "M", "CA", RiskCategory.MODERATE),
                    new Member("Member-1003", 67, "F", "FL", RiskCategory.HIGH),
                    new Member("Member-1004", 31, "F", "IL", RiskCategory.LOW),
                    new Member("Member-1005", 52, "M", "TX", RiskCategory.MODERATE),
                    new Member("Member-1006", 39, "M", "CA", RiskCategory.LOW),
                    new Member("Member-1007", 61, "F", "NY", RiskCategory.HIGH),
                    new Member("Member-1008", 47, "F", "FL", RiskCategory.MODERATE)
            ));
            enrollments.saveAll(List.of(
                    new Enrollment("Member-1001", "Gold PPO", LocalDate.of(2025, 1, 1), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1002", "Gold PPO", LocalDate.of(2025, 2, 10), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1003", "Medicare Advantage", LocalDate.of(2024, 10, 1), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1004", "Silver HMO", LocalDate.of(2026, 1, 8), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1005", "Gold PPO", LocalDate.of(2025, 7, 15), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1006", "Silver HMO", LocalDate.of(2025, 9, 1), CoverageStatus.LAPSED),
                    new Enrollment("Member-1007", "Medicare Advantage", LocalDate.of(2024, 4, 1), CoverageStatus.ACTIVE),
                    new Enrollment("Member-1008", "Gold PPO", LocalDate.of(2026, 3, 1), CoverageStatus.PENDING)
            ));
            claims.saveAll(List.of(
                    claim("Member-1001", "Humira", "Specialty", "6200.00", 5),
                    claim("Member-1001", "Insulin", "Diabetes", "840.00", 19),
                    claim("Member-1002", "Ozempic", "Diabetes", "1100.00", 8),
                    claim("Member-1002", "Humira", "Specialty", "5900.00", 38),
                    claim("Member-1003", "Eliquis", "Cardiovascular", "720.00", 13),
                    claim("Member-1003", "Insulin", "Diabetes", "790.00", 28),
                    claim("Member-1004", "Generic", "Maintenance", "86.00", 7),
                    claim("Member-1005", "Ozempic", "Diabetes", "1050.00", 16),
                    claim("Member-1005", "Humira", "Specialty", "6100.00", 42),
                    claim("Member-1006", "Generic", "Maintenance", "120.00", 24),
                    claim("Member-1007", "Humira", "Specialty", "6400.00", 11),
                    claim("Member-1008", "Insulin", "Diabetes", "810.00", 31)
            ));
        };
    }

    private PharmacyClaim claim(String member, String drug, String category, String cost, int daysAgo) {
        return new PharmacyClaim(member, drug, category, new BigDecimal(cost), LocalDate.now().minusDays(daysAgo));
    }
}
