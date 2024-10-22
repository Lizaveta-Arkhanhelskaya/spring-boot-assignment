package com.arkhanhelskaya.freelancer.repository;

import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, UUID> {

    List<Freelancer> findAllByStatus(String status);

    @Query("select f from Freelancer f where f.status = 'DELETED' and f.deletedDate >= :deletedDate")
    List<Freelancer> findAllDeletedFreelancersWithDeletedDateAfter(
            @Param("deletedDate") Date deletedDate);
}
