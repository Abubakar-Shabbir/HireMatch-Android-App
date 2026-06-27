package com.example.hirematch.models;

public class Application {

    private String applicationId;

    // Job Info
    private String jobId;
    private String jobTitle;
    private String hrId;

    // Candidate Info
    private String candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private String resumeUrl;

    // ATS & Profile
    private int atsScore;
    private int profileScore;

    // Workflow
    private String applicationStatus;
    private String currentStage;

    // HR Decision
    private String decisionReason;
    private String hrNotes;

    // Interview
    private String interviewDate;

    // Offer
    private String offerStatus;

    // Time
    private long appliedAt;
    private long updatedAt;

    public Application() {
    }

    public Application(
            String applicationId,
            String jobId,
            String jobTitle,
            String hrId,
            String candidateId,
            String candidateName,
            String candidateEmail,
            String candidatePhone,
            String resumeUrl,
            int atsScore,
            int profileScore,
            String applicationStatus,
            String currentStage,
            String decisionReason,
            String hrNotes,
            String interviewDate,
            String offerStatus,
            long appliedAt,
            long updatedAt
    ) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.hrId = hrId;

        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhone = candidatePhone;
        this.resumeUrl = resumeUrl;

        this.atsScore = atsScore;
        this.profileScore = profileScore;

        this.applicationStatus = applicationStatus;
        this.currentStage = currentStage;

        this.decisionReason = decisionReason;
        this.hrNotes = hrNotes;

        this.interviewDate = interviewDate;
        this.offerStatus = offerStatus;

        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getHrId() {
        return hrId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public String getCandidatePhone() {
        return candidatePhone;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public int getAtsScore() {
        return atsScore;
    }

    public int getProfileScore() {
        return profileScore;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public String getDecisionReason() {
        return decisionReason;
    }

    public String getHrNotes() {
        return hrNotes;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public long getAppliedAt() {
        return appliedAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    public void setHrNotes(String hrNotes) {
        this.hrNotes = hrNotes;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }


    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}