package com.arkhanhelskaya.freelancer.service;

import com.arkhanhelskaya.freelancer.exception.NotFoundException;
import com.arkhanhelskaya.freelancer.model.EventType;
import com.arkhanhelskaya.freelancer.model.FreelancerRequest;
import com.arkhanhelskaya.freelancer.model.FreelancerResponse;
import com.arkhanhelskaya.freelancer.model.FreelancerStatus;
import com.arkhanhelskaya.freelancer.repository.FreelancerRepository;
import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import com.arkhanhelskaya.freelancer.utils.FreelancerConverter;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FreelancerServiceTest {

    @InjectMocks
    private FreelancerService testee;
    @Mock
    private FreelancerRepository repository;
    @Mock
    private FreelancerConverter converter;
    @Mock
    private NotificationService notificationService;

    @Test
    public void createFreelancer(){
        var request = new FreelancerRequest();

        var id = UUID.randomUUID();
        var savedFreelancer = new Freelancer();
        savedFreelancer.setId(id);

        var response = new FreelancerResponse();

        ArgumentCaptor<Freelancer> captor = ArgumentCaptor.forClass(Freelancer.class);

        when(repository.save(captor.capture())).thenReturn(savedFreelancer);
        when(converter.convert(eq(savedFreelancer))).thenReturn(response);

        var result = testee.createFreelancer(request);

        verify(converter).populate(any(), eq(request));
        verify(repository).save(any());
        verify(notificationService).sendMessage(eq(id), eq(EventType.CREATED));
        verify(converter).convert(eq(savedFreelancer));

        assertEquals(response, result);

        var freelancerCapt = captor.getValue();
        assertNotNull(freelancerCapt);
        assertEquals(FreelancerStatus.NEW_FREELANCER.name(), freelancerCapt.getStatus());
    }

    @Test
    public void updateFreelancer(){
        var id = UUID.randomUUID();
        var request = new FreelancerRequest();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.NEW_FREELANCER.name());

        var savedEntity = new Freelancer();
        savedEntity.setId(id);

        var response = new FreelancerResponse();

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));
        when(repository.save(eq(freelancerEntity))).thenReturn(savedEntity);
        when(converter.convert(eq(savedEntity))).thenReturn(response);

        var result = testee.updateFreelancer(id, request);

        verify(repository).findById(eq(id));
        verify(converter).populate(eq(freelancerEntity), eq(request));
        verify(repository).save(eq(freelancerEntity));
        verify(notificationService).sendMessage(eq(id), eq(EventType.UPDATED));
        verify(converter).convert(eq(savedEntity));

        assertEquals(response, result);
    }

    @Test
    public void updateFreelancer_not_found() {
        var id = UUID.randomUUID();

        assertThrows(NotFoundException.class, () -> testee.updateFreelancer(id, new FreelancerRequest()));

        verify(repository).findById(eq(id));
        verify(converter, never()).populate(any(), any());
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void updateFreelancer_already_deleted() {
        var id = UUID.randomUUID();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.DELETED.name());

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));

        assertThrows(NotFoundException.class, () -> testee.updateFreelancer(id, new FreelancerRequest()));

        verify(repository).findById(eq(id));
        verify(converter, never()).populate(any(), any());
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void deleteFreelancer(){
        var id = UUID.randomUUID();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.NEW_FREELANCER.name());

        var savedEntity = new Freelancer();
        savedEntity.setId(id);

        var response = new FreelancerResponse();

        ArgumentCaptor<Freelancer> captor = ArgumentCaptor.forClass(Freelancer.class);

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));
        when(repository.save(captor.capture())).thenReturn(savedEntity);
        when(converter.convert(eq(savedEntity))).thenReturn(response);

        var result = testee.deleteFreelancer(id);

        verify(repository).findById(eq(id));
        verify(repository).save(any());
        verify(notificationService).sendMessage(eq(id), eq(EventType.DELETED));
        verify(converter).convert(eq(savedEntity));

        assertEquals(response, result);

        var freelancerCapt = captor.getValue();
        assertNotNull(freelancerCapt);
        assertEquals(FreelancerStatus.DELETED.name(), freelancerCapt.getStatus());
        assertNotNull(freelancerCapt.getDeletedDate());
    }

    @Test
    public void deleteFreelancer_not_found() {
        var id = UUID.randomUUID();

        assertThrows(NotFoundException.class, () -> testee.deleteFreelancer(id));

        verify(repository).findById(eq(id));
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void deleteFreelancer_already_deleted() {
        var id = UUID.randomUUID();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.DELETED.name());

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));

        assertThrows(NotFoundException.class, () -> testee.deleteFreelancer(id));

        verify(repository).findById(eq(id));
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void verifyFreelancer(){
        var id = UUID.randomUUID();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.NEW_FREELANCER.name());

        var savedEntity = new Freelancer();
        savedEntity.setId(id);

        var response = new FreelancerResponse();

        ArgumentCaptor<Freelancer> captor = ArgumentCaptor.forClass(Freelancer.class);

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));
        when(repository.save(captor.capture())).thenReturn(savedEntity);
        when(converter.convert(eq(savedEntity))).thenReturn(response);

        var result = testee.verifyFreelancer(id);

        verify(repository).findById(eq(id));
        verify(repository).save(any());
        verify(notificationService).sendMessage(eq(id), eq(EventType.VERIFIED));
        verify(converter).convert(eq(savedEntity));

        assertEquals(response, result);

        var freelancerCapt = captor.getValue();
        assertNotNull(freelancerCapt);
        assertEquals(FreelancerStatus.VERIFIED.name(), freelancerCapt.getStatus());
        assertNull(freelancerCapt.getDeletedDate());
    }

    @Test
    public void verifyFreelancer_not_found() {
        var id = UUID.randomUUID();

        assertThrows(NotFoundException.class, () -> testee.verifyFreelancer(id));

        verify(repository).findById(eq(id));
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void verifyFreelancer_already_deleted() {
        var id = UUID.randomUUID();

        var freelancerEntity = new Freelancer();
        freelancerEntity.setId(id);
        freelancerEntity.setStatus(FreelancerStatus.DELETED.name());

        when(repository.findById(eq(id))).thenReturn(Optional.of(freelancerEntity));

        assertThrows(NotFoundException.class, () -> testee.verifyFreelancer(id));

        verify(repository).findById(eq(id));
        verify(repository, never()).save(any());
        verify(notificationService, never()).sendMessage(any(), any());
        verify(converter, never()).convert(any(Freelancer.class));
    }

    @Test
    public void getNewFreelancers(){
        List<Freelancer> entityList = Collections.EMPTY_LIST;
        List<FreelancerResponse> responseList = Collections.EMPTY_LIST;

        when(repository.findAllByStatus(eq(FreelancerStatus.NEW_FREELANCER.name()))).thenReturn(entityList);
        when(converter.convert(eq(entityList))).thenReturn(responseList);

        var result = testee.getNewFreelancers();

        verify(repository).findAllByStatus(eq(FreelancerStatus.NEW_FREELANCER.name()));
        verify(converter).convert(entityList);

        assertEquals(responseList, result);
    }

    @Test
    public void getDeletedFreelancers(){
        List<Freelancer> entityList = Collections.EMPTY_LIST;
        List<FreelancerResponse> responseList = Collections.EMPTY_LIST;

        when(repository.findAllDeletedFreelancersWithDeletedDateAfter(any())).thenReturn(entityList);
        when(converter.convert(eq(entityList))).thenReturn(responseList);

        var result = testee.getDeletedFreelancers();

        verify(repository).findAllDeletedFreelancersWithDeletedDateAfter(any());
        verify(converter).convert(entityList);

        assertEquals(responseList, result);
    }
}
