package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Execution;
import io.github.anxolerd.jee.repository.ExecutionRepo;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Log4j2
@Transactional
@ApplicationScoped
public class JpaExecutionRepoImpl implements ExecutionRepo {
    private final EntityManager em;

    @Inject
    public JpaExecutionRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Execution execution) {
        log.debug(String.format("Create new execution %s", execution));
        em.persist(execution);
    }

    @Override
    public void update(Execution execution) {
        log.debug(String.format("Update execution %s", execution));
        em.merge(execution);
    }

    @Override
    public Execution findById(long id) {
        log.debug(String.format("Get execution by id %d", id));
        return em.find(Execution.class, id);
    }
}
