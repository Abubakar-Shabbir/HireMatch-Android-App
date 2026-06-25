package com.example.hirematch.models;

public class Application {

    private String applicationId;
    private String jobId;
    private String candidateId;
    private String hrId;
    private String candidateName;
    private String candidateEmail;
    private String applicationStatus;
    private String appliedAt;
    private int atsScore;

    public Application() {
    }

    public Application(
            String applicationId,
            String jobId,
            String candidateId,
            String hrId,
            String candidateName,
            String candidateEmail,
            String applicationStatus,
            String appliedAt,
            int atsScore) {

        this.applicationId = applicationId;
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.hrId = hrId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.applicationStatus = applicationStatus;
        this.appliedAt = appliedAt;
        this.atsScore = atsScore;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getHrId() {
        return hrId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String getAppliedAt() {
        return appliedAt;
    }

    public int getAtsScore() {
        return atsScore;
    }
}