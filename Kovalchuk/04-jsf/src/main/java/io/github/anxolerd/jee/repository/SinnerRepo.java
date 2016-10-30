package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Sinner;

import java.util.List;


public interface SinnerRepo {
    void create(Sinner sinner);

    void update(Sinner sinner);

    List<Sinner> findAll();

    Sinner findById(long id);
}
