package com.example.hirematch.models;

public class Job {

    private String jobId;
    private String hrId;

    private String companyId;
    private String companyName;

    private String jobTitle;
    private String jobDescription;

    private String jobType;
    private String workMode;

    private String salaryMin;
    private String salaryMax;

    private String location;
    private String experienceLevel;

    private String requiredSkills;
    private String educationRequirement;

    private String vacancies;
    private String deadline;

    private String benefits;
    private String status;

    private String createdAt;

    public Job() {
    }

    public Job(
            String jobId,
            String hrId,
            String companyId,
            String companyName,
            String jobTitle,
            String jobDescription,
            String jobType,
            String workMode,
            String salaryMin,
            String salaryMax,
            String location,
            String experienceLevel,
            String requiredSkills,
            String educationRequirement,
            String vacancies,
            String deadline,
            String benefits,
            String status,
            String createdAt
    ) {
        this.jobId = jobId;
        this.hrId = hrId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobType = jobType;
        this.workMode = workMode;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.location = location;
        this.experienceLevel = experienceLevel;
        this.requiredSkills = requiredSkills;
        this.educationRequirement = educationRequirement;
        this.vacancies = vacancies;
        this.deadline = deadline;
        this.benefits = benefits;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getJobId() {
        return jobId;
    }

    public String getHrId() {
        return hrId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobType() {
        return jobType;
    }

    public String getWorkMode() {
        return workMode;
    }

    public String getSalaryMin() {
        return salaryMin;
    }

    public String getSalaryMax() {
        return salaryMax;
    }

    public String getLocation() {
        return location;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public String getEducationRequirement() {
        return educationRequirement;
    }

    public String getVacancies() {
        return vacancies;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getBenefits() {
        return benefits;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    /*
    =========================================
    BACKWARD COMPATIBILITY METHODS
    OLD FILES WON'T BREAK
    =========================================
    */

    public String getTitle() {
        return jobTitle;
    }


    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDescription() {
        return jobDescription;
    }

    public String getSkills() {
        return requiredSkills;
    }

    public String getExperience() {
        return experienceLevel;
    }

    public String getSalary() {
        return salaryMin + " - " + salaryMax;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public void setEducationRequirement(String educationRequirement) {
        this.educationRequirement = educationRequirement;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}