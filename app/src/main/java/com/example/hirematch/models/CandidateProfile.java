package com.example.hirematch.models;

public class CandidateProfile {

    private String uid;
    private String name;
    private String email;
    private String phone;
    private String city;

    private String skills;
    private String education;
    private String experience;
    private String resumeUrl;

    public CandidateProfile() {
    }

    public CandidateProfile(String uid,
                            String name,
                            String email,
                            String phone,
                            String city,
                            String skills,
                            String education,
                            String experience,
                            String resumeUrl) {

        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.resumeUrl = resumeUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}