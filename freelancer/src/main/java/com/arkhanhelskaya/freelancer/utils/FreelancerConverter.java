package com.arkhanhelskaya.freelancer.utils;

import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.model.FreelancerStatus;
import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreelancerConverter {

    public void populate(Freelancer entity, FreelancerRequest source){
        entity.setFirstName(source.getFirstName());
        entity.setLastName(source.getLastName());
        entity.setDateOfBirth(new Date(source.getDateOfBirth().getTime()));
        entity.setGender(source.getGender());
    }

    public FreelancerResponse convert(Freelancer entity){
        var response = new FreelancerResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setDateOfBirth(new java.util.Date(entity.getDateOfBirth().getTime()));
        response.setGender(entity.getGender());
        response.setStatus(FreelancerStatus.valueOf(entity.getStatus()));
        if(entity.getDeletedDate() != null) {
            response.setDeletedDate(new java.util.Date(entity.getDeletedDate().getTime()));
        }
        return response;
    }

    public List<FreelancerResponse> convert(List<Freelancer> entities){
        return entities.stream().map(this::convert).collect(Collectors.toList());
    }
}
