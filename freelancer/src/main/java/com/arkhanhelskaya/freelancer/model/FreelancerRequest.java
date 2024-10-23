package com.arkhanhelskaya.freelancer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class FreelancerRequest {
    @Schema(description = "First name", example = "Marry")
    @NotNull
    private String firstName;
    @Schema(description = "Last name", example = "Black")
    @NotNull
    private String lastName;
    @Schema(description = "Date of birth", type = "string",
            pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", example = "01-01-1990")
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    @Schema(description = "Gender", example = "female")
    @NotNull
    private String gender;

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
}
