package com.vitalsync.api.repository;

import com.vitalsync.api.domain.CoverageStatus;
import com.vitalsync.api.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findFirstByMemberIdOrderByEnrollmentDateDesc(String memberId);
    long countByCoverageStatus(CoverageStatus status);
    List<Enrollment> findByCoverageStatus(CoverageStatus status);
}
