package com.example.hirematch.models;

public class Company {

    private String hrId;
    private String companyName;
    private String website;
    private String description;
    private String location;

    private String industry;
    private String companySize;
    private String foundedYear;
    private String linkedIn;

    private String benefits;
    private String culture;
    private String hiringEmail;

    private String companyLogo;
    private String createdAt;

    public Company() {
    }

    public Company(
            String hrId,
            String companyName,
            String website,
            String description,
            String location,
            String industry,
            String companySize,
            String foundedYear,
            String linkedIn,
            String benefits,
            String culture,
            String hiringEmail,
            String companyLogo,
            String createdAt
    ) {
        this.hrId = hrId;
        this.companyName = companyName;
        this.website = website;
        this.description = description;
        this.location = location;
        this.industry = industry;
        this.companySize = companySize;
        this.foundedYear = foundedYear;
        this.linkedIn = linkedIn;
        this.benefits = benefits;
        this.culture = culture;
        this.hiringEmail = hiringEmail;
        this.companyLogo = companyLogo;
        this.createdAt = createdAt;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(String foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getHiringEmail() {
        return hiringEmail;
    }

    public void setHiringEmail(String hiringEmail) {
        this.hiringEmail = hiringEmail;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}