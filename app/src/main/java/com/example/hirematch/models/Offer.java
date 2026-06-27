package com.example.hirematch.models;

public class Offer {

    private String offerId;
    private String applicationId;
    private String candidateId;
    private String candidateName;
    private String hrId;
    private String jobId;
    private String jobTitle;

    private String salary;
    private String joiningDate;
    private String offerLetter;

    private String offerStatus;
    private String createdAt;

    public Offer() {
    }

    public Offer(
            String offerId,
            String applicationId,
            String candidateId,
            String candidateName,
            String hrId,
            String jobId,
            String jobTitle,
            String salary,
            String joiningDate,
            String offerLetter,
            String offerStatus,
            String createdAt
    ) {

        this.offerId = offerId;
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.hrId = hrId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;

        this.salary = salary;
        this.joiningDate = joiningDate;
        this.offerLetter = offerLetter;

        this.offerStatus = offerStatus;
        this.createdAt = createdAt;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getOfferLetter() {
        return offerLetter;
    }

    public void setOfferLetter(String offerLetter) {
        this.offerLetter = offerLetter;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}