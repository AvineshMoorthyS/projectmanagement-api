package com.projectmanagement.enums;

public enum Status {

    RequirementsGathering("RequirementsGathering"),
    New("New"),
    InProgress("InProgress"),
    Hold("Hold"),
    InQA("InQA"),
    Completed("Completed");

    private final String status;
    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

}
