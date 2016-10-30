package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Accuse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@Transactional
@ApplicationScoped
public class JpaAccuseRepoImpl implements AccuseRepo {
    private final EntityManager em;

    @Inject
    public JpaAccuseRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void update(@NonNull Accuse accuse) {
        log.debug(String.format("Update accuse %s", accuse));
        em.merge(accuse);
    }

    @Override
    public void create(@NonNull Accuse accuse) {
        log.debug(String.format("Create new accuse %s", accuse));
        em.persist(accuse);
    }
}
