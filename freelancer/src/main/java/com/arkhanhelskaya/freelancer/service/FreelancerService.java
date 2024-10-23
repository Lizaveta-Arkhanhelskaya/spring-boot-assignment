package com.arkhanhelskaya.freelancer.service;

import com.arkhanhelskaya.freelancer.exception.NotFoundException;
import com.arkhanhelskaya.freelancer.model.EventType;
import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.model.FreelancerStatus;
import com.arkhanhelskaya.freelancer.repository.FreelancerRepository;
import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import com.arkhanhelskaya.freelancer.utils.FreelancerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FreelancerService {

    public static final int DELETION_PERIOD = 7;

    @Autowired
    private FreelancerRepository repository;
    @Autowired
    private FreelancerConverter converter;
    @Autowired
    private NotificationService notificationService;

    public FreelancerResponse createFreelancer(FreelancerRequest freelancer) {
        var entity = new Freelancer();
        converter.populate(entity, freelancer);
        entity.setStatus(FreelancerStatus.NEW_FREELANCER.name());
        return saveFreelancer(entity, EventType.CREATED);
    }

    public FreelancerResponse updateFreelancer(UUID id, FreelancerRequest freelancer) {
        var entity = getFreelancer(id);
        converter.populate(entity, freelancer);
        return saveFreelancer(entity, EventType.UPDATED);
    }

    public FreelancerResponse deleteFreelancer(UUID id) {
        var entity = getFreelancer(id);
        entity.setStatus(FreelancerStatus.DELETED.name());
        entity.setDeletedDate(new Date(System.currentTimeMillis()));
        return saveFreelancer(entity, EventType.DELETED);
    }

    public List<FreelancerResponse> getNewFreelancers() {
        var entities = repository.findAllByStatus(FreelancerStatus.NEW_FREELANCER.name());
        return converter.convert(entities);
    }

    public List<FreelancerResponse> getDeletedFreelancers() {
        var entities = repository.findAllDeletedFreelancersWithDeletedDateAfter(
                Date.valueOf(LocalDate.now().minusDays(DELETION_PERIOD)));
        return converter.convert(entities);
    }

    public FreelancerResponse verifyFreelancer(UUID id) {
        var entity = getFreelancer(id);
        entity.setStatus(FreelancerStatus.VERIFIED.name());
        return saveFreelancer(entity, EventType.VERIFIED);
    }

    private Freelancer getFreelancer(UUID id){
        var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Freelancer is not found. "));
        if (entity.getStatus().equals(FreelancerStatus.DELETED.name())){
            throw new NotFoundException("Freelancer is already deleted. ");
        }
        return entity;
    }

    private FreelancerResponse saveFreelancer(Freelancer entity, EventType event){
        var savedEntity = repository.save(entity);
        notificationService.sendMessage(savedEntity.getId(), event);
        return converter.convert(savedEntity);
    }
}
