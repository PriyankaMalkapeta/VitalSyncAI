package com.vitalsync.api.web;

import com.vitalsync.api.service.HealthcareAnalyticsService;
import com.vitalsync.api.web.ApiModels.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class HealthcareController {
    private final HealthcareAnalyticsService analytics;

    public HealthcareController(HealthcareAnalyticsService analytics) { this.analytics = analytics; }

    @GetMapping("/dashboard")
    public DashboardSummary dashboard() { return analytics.dashboard(); }

    @GetMapping("/members")
    public List<MemberSummary> members() { return analytics.memberSummaries(); }

    @GetMapping("/members/{memberId}")
    public Member360 member(@PathVariable String memberId) { return analytics.member360(memberId); }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(NoSuchElementException error) {
        return Map.of("error", error.getMessage());
    }
}
