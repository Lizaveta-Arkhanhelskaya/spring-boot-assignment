package com.arkhanhelskaya.freelancer.repository;

import com.arkhanhelskaya.freelancer.model.FreelancerStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FreelancerRepositoryTest {

    @Autowired
    private FreelancerRepository testee;

    @Test
    public void findAllByStatus(){
        var freelancers = testee.findAllByStatus(FreelancerStatus.NEW_FREELANCER.name());
        assertNotNull(freelancers);
        assertEquals(2, freelancers.size());
        freelancers.forEach(freelancer -> assertEquals(FreelancerStatus.NEW_FREELANCER.name(), freelancer.getStatus()));
    }

    @Test
    public void findAllDeletedFreelancersWithDeletedDateAfter(){
        var freelancers = testee.findAllDeletedFreelancersWithDeletedDateAfter(Date.valueOf("2024-10-15"));
        assertNotNull(freelancers);
        assertEquals(1, freelancers.size());
        freelancers.forEach(freelancer -> assertEquals(FreelancerStatus.DELETED.name(), freelancer.getStatus()));
    }
}
