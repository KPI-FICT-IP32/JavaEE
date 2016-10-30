package io.github.anxolerd.jee.controller;


import io.github.anxolerd.jee.model.Sinner;
import io.github.anxolerd.jee.repository.SinnerRepo;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
@Log4j2
public class SinnersListViewController {
    final SinnerRepo repo;

    @Inject
    public SinnersListViewController(SinnerRepo repo) {
        this.repo = repo;
    }

    public List<Sinner> getAll() {
        log.debug("Requesting the list of all sinners");
        return repo.findAll();
    }
}
