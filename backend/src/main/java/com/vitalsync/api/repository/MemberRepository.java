package com.vitalsync.api.repository;

import com.vitalsync.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {}
