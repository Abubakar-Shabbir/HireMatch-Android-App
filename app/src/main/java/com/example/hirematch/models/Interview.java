package com.example.hirematch.models;

public class Interview {

    private String interviewId;
    private String applicationId;
    private String jobId;
    private String candidateId;
    private String hrId;
    private String jobTitle;
    private String interviewDate;
    private String interviewTime;
    private String interviewStatus;
    private String meetingLink;
    private String createdAt;

    public Interview() {
    }

    public Interview(
            String interviewId,
            String applicationId,
            String jobId,
            String candidateId,
            String hrId,
            String jobTitle,
            String interviewDate,
            String interviewTime,
            String interviewStatus,
            String meetingLink,
            String createdAt) {

        this.interviewId = interviewId;
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.hrId = hrId;
        this.jobTitle = jobTitle;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.interviewStatus = interviewStatus;
        this.meetingLink = meetingLink;
        this.createdAt = createdAt;
    }

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

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}