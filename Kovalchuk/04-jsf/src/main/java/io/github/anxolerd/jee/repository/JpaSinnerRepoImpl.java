package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Sinner;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Log4j2
@Transactional
@ApplicationScoped
public class JpaSinnerRepoImpl implements SinnerRepo {
    private final EntityManager em;

    @Inject
    public JpaSinnerRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(@NonNull Sinner sinner) {
        log.debug(String.format("Create sinner %s", sinner));
        em.persist(sinner);
    }

    @Override
    public void update(@NonNull Sinner sinner) {
        log.debug(String.format("Update sinner %s", sinner));
        em.merge(sinner);
    }

    private List<Sinner> toSinnersList(Collection c) {
        List<Sinner> sinners = new ArrayList<>();
        for (Object o : c) {
            sinners.add((Sinner) o);
        }
        return sinners;
    }

    @Override
    public List<Sinner> findAll() {
        log.debug("Get all sinners");
        val query = em.getCriteriaBuilder().createQuery(Sinner.class);
        val sinner = query.from(Sinner.class);
        query.select(sinner);
        return em.createQuery(query).getResultList();
    }

    @Override
    public Sinner findById(long id) {
        return em.find(Sinner.class, id);
    }
}
