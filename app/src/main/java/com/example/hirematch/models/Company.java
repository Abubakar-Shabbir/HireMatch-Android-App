package com.example.hirematch.models;

public class Company {

    private String hrId;
    private String companyName;
    private String website;
    private String description;
    private String location;

    public Company() {
    }

    public Company(String hrId,
                   String companyName,
                   String website,
                   String description,
                   String location) {

        this.hrId = hrId;
        this.companyName = companyName;
        this.website = website;
        this.description = description;
        this.location = location;
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
}