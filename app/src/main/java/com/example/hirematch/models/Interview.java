package com.example.hirematch.models;

public class Interview {

    private String interviewId;
    private String applicationId;
    private String jobId;
    private String candidateId;
    private String candidateName;
    private String hrId;
    private String jobTitle;

    private String interviewDate;
    private String interviewTime;

    private String interviewMode;
    private String meetingLink;
    private String location;
    private String notes;

    private String interviewStatus;
    private String createdAt;

    public Interview() {
    }

    public Interview(
            String interviewId,
            String applicationId,
            String jobId,
            String candidateId,
            String candidateName,
            String hrId,
            String jobTitle,
            String interviewDate,
            String interviewTime,
            String interviewMode,
            String meetingLink,
            String location,
            String notes,
            String interviewStatus,
            String createdAt
    ) {

        this.interviewId = interviewId;
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.hrId = hrId;
        this.jobTitle = jobTitle;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.interviewMode = interviewMode;
        this.meetingLink = meetingLink;
        this.location = location;
        this.notes = notes;
        this.interviewStatus = interviewStatus;
        this.createdAt = createdAt;
    }

    // ==========================
    // GETTERS
    // ==========================

    public String getInterviewId() {
        return interviewId;
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

    public String getCandidateName() {
        return candidateName;
    }

    public String getHrId() {
        return hrId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public String getInterviewTime() {
        return interviewTime;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    public void setInterviewMode(String interviewMode) {
        this.interviewMode = interviewMode;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
