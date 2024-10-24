package com.arkhanhelskaya.freelancer.controller;

import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.service.FreelancerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.arkhanhelskaya.freelancer.security.SecurityConfig.FREELANCER_ROLE;
import static com.arkhanhelskaya.freelancer.security.SecurityConfig.STAFF_ROLE;

@RestController
@RequestMapping(path = "/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerService service;

    @Operation(description = "Create a new freelancer")
    @Tag(name = "freelancer", description = "Freelancer role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PreAuthorize(FREELANCER_ROLE)
    @PostMapping
    public @ResponseBody FreelancerResponse createFreelancer(@RequestBody FreelancerRequest freelancer) {
        return service.createFreelancer(freelancer);
    }

    @Operation(description = "Update a freelancer")
    @Tag(name = "freelancer", description = "Freelancer role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The freelancer was not found or deleted. ")
    })
    @PreAuthorize(FREELANCER_ROLE)
    @PutMapping(path = "/{id}")
    public @ResponseBody FreelancerResponse updateFreelancer(@PathVariable("id") @Parameter(name = "id", description = "Freelancer id",
            example = "e18d9303-861d-459a-a94f-9dc30060d73d") UUID id, @RequestBody FreelancerRequest freelancer) {
        return service.updateFreelancer(id, freelancer);
    }

    @Operation(description = "Delete a freelancer")
    @Tag(name = "freelancer", description = "Freelancer role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The freelancer was not found or deleted. ")
    })
    @PreAuthorize(FREELANCER_ROLE)
    @DeleteMapping(path = "/{id}")
    public @ResponseBody FreelancerResponse deleteFreelancer(@PathVariable("id") @Parameter(name = "id", description = "Freelancer id",
            example = "e18d9303-861d-459a-a94f-9dc30060d73d") UUID id) {
        return service.deleteFreelancer(id);
    }

    @Operation(description = "Get all the newly registered freelancers")
    @Tag(name = "staff-user", description = "Staff role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrived"),
    })
    @PreAuthorize(STAFF_ROLE)
    @GetMapping(path = "/new")
    public @ResponseBody List<FreelancerResponse> getNewFreelancers() {
        return service.getNewFreelancers();
    }

    @Operation(description = "Get all deleted freelancer in last 7 days")
    @Tag(name = "staff-user", description = "Staff role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @PreAuthorize(STAFF_ROLE)
    @GetMapping(path = "/deleted")
    public @ResponseBody List<FreelancerResponse> getDeletedFreelancers() {
        return service.getDeletedFreelancers();
    }

    @Operation(description = "Update the freelancer’s status to VERIFIED")
    @Tag(name = "staff-user", description = "Staff role APIs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The freelancer was not found or deleted. ")
    })
    @PreAuthorize(STAFF_ROLE)
    @PutMapping(path = "/{id}/verify")
    public @ResponseBody FreelancerResponse verifyFreelancer(@PathVariable("id") @Parameter(name = "id", description = "Freelancer id",
            example = "e18d9303-861d-459a-a94f-9dc30060d73d") UUID id) {
        return service.verifyFreelancer(id);
    }
}
