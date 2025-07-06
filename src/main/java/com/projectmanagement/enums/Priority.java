package com.projectmanagement.enums;

public enum Priority {

    low("low"),
    medium("medium"),
    high("high"),
    critical("critical");

    private final String priority;
    Priority(String priority) {
        this.priority = priority;
    }
    public String getPriority() {
        return priority;
    }

}
