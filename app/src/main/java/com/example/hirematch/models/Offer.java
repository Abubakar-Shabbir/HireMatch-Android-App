package com.example.hirematch.models;

public class Offer {

    private String offerId;
    private String applicationId;
    private String candidateId;
    private String hrId;
    private String jobTitle;
    private String salary;
    private String joiningDate;
    private String offerStatus;
    private String createdAt;

    public Offer() {
    }

    public Offer(
            String offerId,
            String applicationId,
            String candidateId,
            String hrId,
            String jobTitle,
            String salary,
            String joiningDate,
            String offerStatus,
            String createdAt) {

        this.offerId = offerId;
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.hrId = hrId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.offerStatus = offerStatus;
        this.createdAt = createdAt;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getHrId() {
        return hrId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}