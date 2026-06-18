package com.vitalsync.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HealthcareApiTest {
    @Autowired MockMvc mvc;

    @Test
    void dashboardReturnsGovernedMetrics() throws Exception {
        mvc.perform(get("/api/v1/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalMembers").value(8))
                .andExpect(jsonPath("$.activeEnrollments").value(6))
                .andExpect(jsonPath("$.spendByPlan[0].plan").value("Gold PPO"));
    }

    @Test
    void member360CombinesAllThreeSources() throws Exception {
        mvc.perform(get("/api/v1/members/Member-1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("Member-1001"))
                .andExpect(jsonPath("$.enrollment.planType").value("Gold PPO"))
                .andExpect(jsonPath("$.pharmacyClaims.length()").value(2))
                .andExpect(jsonPath("$.lineage.pharmacy").value("PBM"));
    }
}
