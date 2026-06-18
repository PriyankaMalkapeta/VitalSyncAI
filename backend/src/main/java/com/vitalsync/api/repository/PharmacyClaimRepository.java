package com.vitalsync.api.repository;

import com.vitalsync.api.domain.PharmacyClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PharmacyClaimRepository extends JpaRepository<PharmacyClaim, Long> {
    List<PharmacyClaim> findByMemberIdOrderByFillDateDesc(String memberId);
}
