package com.projectmanagement.enums;

public enum Plan {

    free("free"),
    pro("pro"),
    premium("premium");

    private final String plan;

    Plan(String plan) {
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

}
