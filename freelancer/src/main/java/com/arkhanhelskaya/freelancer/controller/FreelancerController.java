package com.arkhanhelskaya.freelancer.controller;

import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerService service;

    @PostMapping
    public FreelancerResponse createFreelancer(@RequestBody FreelancerRequest freelancer) {
        return service.createFreelancer(freelancer);
    }

    @PutMapping(path = "/{id}")
    public FreelancerResponse updateFreelancer(@PathVariable("id") UUID id, @RequestBody FreelancerRequest freelancer) {
        return service.updateFreelancer(id, freelancer);
    }

    @DeleteMapping(path = "/{id}")
    public FreelancerResponse deleteFreelancer(@PathVariable("id") UUID id) {
        return service.deleteFreelancer(id);
    }

    @GetMapping(path = "/new")
    public List<FreelancerResponse> getNewFreelancers() {
        return service.getNewFreelancers();
    }

    @GetMapping(path = "/deleted")
    public List<FreelancerResponse> getDeletedFreelancers() {
        return service.getDeletedFreelancers();
    }

    @PutMapping(path = "/{id}/verify")
    public FreelancerResponse verifyFreelancer(@PathVariable("id") UUID id) {
        return service.verifyFreelancer(id);
    }
}
