package com.example.hirematch.models;

public class CandidateProfile {

    private String uid;
    private int profileScore;
    private String name;
    private String email;
    private String phone;
    private String city;

    private String title;
    private String about;

    private String skills;
    private String education;
    private String experience;

    private String certifications;
    private String achievements;
    private String awards;
    private String projects;
    private String portfolio;

    private String expectedSalary;
    private String preferredJobType;
    private String preferredLocation;
    private String availability;
    private String workMode;

    private String profileImage;
    private String resumeUrl;

    private String createdAt;

    public CandidateProfile() {
    }
    public int getProfileScore() {
        return profileScore;
    }
    public CandidateProfile(
            String uid,
            String name,
            String email,
            String phone,
            String city,
            String title,
            String about,
            String skills,
            String education,
            String experience,
            String certifications,
            String achievements,
            String awards,
            String projects,
            String portfolio,
            String expectedSalary,
            String preferredJobType,
            String preferredLocation,
            String availability,
            String workMode,
            String profileImage,
            String resumeUrl,
            String createdAt
    ) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.title = title;
        this.about = about;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.certifications = certifications;
        this.achievements = achievements;
        this.awards = awards;
        this.projects = projects;
        this.portfolio = portfolio;
        this.expectedSalary = expectedSalary;
        this.preferredJobType = preferredJobType;
        this.preferredLocation = preferredLocation;
        this.availability = availability;
        this.workMode = workMode;
        this.profileImage = profileImage;
        this.resumeUrl = resumeUrl;
        this.createdAt = createdAt;
    }


    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }

    public String getAchievements() { return achievements; }
    public void setAchievements(String achievements) { this.achievements = achievements; }

    public String getAwards() { return awards; }
    public void setAwards(String awards) { this.awards = awards; }

    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }

    public String getPortfolio() { return portfolio; }
    public void setPortfolio(String portfolio) { this.portfolio = portfolio; }

    public String getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(String expectedSalary) { this.expectedSalary = expectedSalary; }

    public String getPreferredJobType() { return preferredJobType; }
    public void setPreferredJobType(String preferredJobType) { this.preferredJobType = preferredJobType; }

    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getWorkMode() { return workMode; }
    public void setWorkMode(String workMode) { this.workMode = workMode; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}