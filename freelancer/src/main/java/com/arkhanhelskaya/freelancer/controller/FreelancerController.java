package com.arkhanhelskaya.freelancer.controller;

import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.service.FreelancerService;
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

    @PreAuthorize(FREELANCER_ROLE)
    @PostMapping
    public FreelancerResponse createFreelancer(@RequestBody FreelancerRequest freelancer) {
        return service.createFreelancer(freelancer);
    }

    @PreAuthorize(FREELANCER_ROLE)
    @PutMapping(path = "/{id}")
    public FreelancerResponse updateFreelancer(@PathVariable("id") UUID id, @RequestBody FreelancerRequest freelancer) {
        return service.updateFreelancer(id, freelancer);
    }

    @PreAuthorize(FREELANCER_ROLE)
    @DeleteMapping(path = "/{id}")
    public FreelancerResponse deleteFreelancer(@PathVariable("id") UUID id) {
        return service.deleteFreelancer(id);
    }

    @PreAuthorize(STAFF_ROLE)
    @GetMapping(path = "/new")
    public List<FreelancerResponse> getNewFreelancers() {
        return service.getNewFreelancers();
    }

    @PreAuthorize(STAFF_ROLE)
    @GetMapping(path = "/deleted")
    public List<FreelancerResponse> getDeletedFreelancers() {
        return service.getDeletedFreelancers();
    }

    @PreAuthorize(STAFF_ROLE)
    @PutMapping(path = "/{id}/verify")
    public FreelancerResponse verifyFreelancer(@PathVariable("id") UUID id) {
        return service.verifyFreelancer(id);
    }
}
