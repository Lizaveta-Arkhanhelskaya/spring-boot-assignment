package com.arkhanhelskaya.freelancer.utils;

import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.model.FreelancerStatus;
import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class FreelancerConverterTest {

    private FreelancerConverter testee = new FreelancerConverter();

    @Test
    public void populate(){
        var entity = createFreelancer();
        var source = createFreelancerRequest();

        testee.populate(entity, source);

        assertEquals(UUID.fromString("e18d9303-861d-459a-a94f-9dc30060d73d"), entity.getId());
        assertEquals("Carry", entity.getFirstName());
        assertEquals("Green", entity.getLastName());
        assertEquals(Date.valueOf("1991-03-12"), entity.getDateOfBirth());
        assertEquals("male", entity.getGender());
        assertEquals(FreelancerStatus.NEW_FREELANCER.name(), entity.getStatus());
        assertNull(entity.getDeletedDate());
    }

    @Test
    public void convert(){
        var result = testee.convert(createFreelancer());
        checkFreelancerResponse(result);
    }

    @Test
    public void convert_list(){
        var result = testee.convert(List.of(createFreelancer(), createFreelancer()));

        assertNotNull(result);
        assertEquals(2, result.size());
        result.forEach(freelancer -> checkFreelancerResponse(freelancer));
    }

    private void checkFreelancerResponse(FreelancerResponse response){
        assertNotNull(response);
        assertEquals(UUID.fromString("e18d9303-861d-459a-a94f-9dc30060d73d"), response.getId());
        assertEquals("Marry", response.getFirstName());
        assertEquals("Black", response.getLastName());
        assertEquals(Date.valueOf("1990-03-12"), response.getDateOfBirth());
        assertEquals("female", response.getGender());
        assertEquals(FreelancerStatus.NEW_FREELANCER, response.getStatus());
        assertNull(response.getDeletedDate());
    }

    private Freelancer createFreelancer(){
        var freelancer = new Freelancer();
        freelancer.setId(UUID.fromString("e18d9303-861d-459a-a94f-9dc30060d73d"));
        freelancer.setFirstName("Marry");
        freelancer.setLastName("Black");
        freelancer.setDateOfBirth(Date.valueOf("1990-03-12"));
        freelancer.setGender("female");
        freelancer.setStatus(FreelancerStatus.NEW_FREELANCER.name());
        return freelancer;
    }

    private FreelancerRequest createFreelancerRequest(){
        var request = new FreelancerRequest();
        request.setFirstName("Carry");
        request.setLastName("Green");
        request.setDateOfBirth(Date.valueOf("1991-03-12"));
        request.setGender("male");
        return request;
    }
}
