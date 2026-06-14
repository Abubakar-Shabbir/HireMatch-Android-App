package com.example.hirematch.models;

public class Job {


    private String jobId;
    private String hrId;
    private String title;
    private String description;
    private String skills;
    private String experience;
    private String location;
    private String salary;
    private String status;

    public Job() {
    }

    public Job(String jobId,
               String hrId,
               String title,
               String description,
               String skills,
               String experience,
               String location,
               String salary,
               String status) {

        this.jobId = jobId;
        this.hrId = hrId;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.location = location;
        this.salary = salary;
        this.status = status;
    }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getHrId() { return hrId; }
    public void setHrId(String hrId) { this.hrId = hrId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


}
