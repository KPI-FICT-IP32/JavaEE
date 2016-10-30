package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Accuse;

import java.util.List;


public interface AccuseRepo {
    void create(Accuse accuse);

    void update(Accuse accuse);
}
