package com.vitalsync.api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    private String id;
    private int age;
    private String gender;
    private String state;
    @Enumerated(EnumType.STRING)
    private RiskCategory riskCategory;

    protected Member() {}

    public Member(String id, int age, String gender, String state, RiskCategory riskCategory) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.state = state;
        this.riskCategory = riskCategory;
    }

    public String getId() { return id; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getState() { return state; }
    public RiskCategory getRiskCategory() { return riskCategory; }
}
