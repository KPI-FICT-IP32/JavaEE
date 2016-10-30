package io.github.anxolerd.jee.repository;

import io.github.anxolerd.jee.model.Execution;


public interface ExecutionRepo {
    void create(Execution execution);

    void update(Execution execution);

    Execution findById(long id);
}
