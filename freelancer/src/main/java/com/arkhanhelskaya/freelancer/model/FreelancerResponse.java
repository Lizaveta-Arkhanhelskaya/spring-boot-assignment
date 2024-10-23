package com.arkhanhelskaya.freelancer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

public class FreelancerResponse {
    @Schema(description = "Freelancer id", example = "e18d9303-861d-459a-a94f-9dc30060d73d")
    private UUID id;
    @Schema(description = "First name", example = "Marry")
    private String firstName;
    @Schema(description = "Last name", example = "Black")
    private String lastName;
    @Schema(description = "Date of birth", type = "string",
            pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", example = "01-01-1990")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    @Schema(description = "Gender", example = "female")
    private String gender;
    @Schema(description = "Status", example = "VERIFIED")
    private FreelancerStatus status;
    @Schema(description = "Date of deletion", type = "string",
            pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", example = "01-01-2024")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date deletedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public FreelancerStatus getStatus() {
        return status;
    }

    public void setStatus(FreelancerStatus status) {
        this.status = status;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }
}
