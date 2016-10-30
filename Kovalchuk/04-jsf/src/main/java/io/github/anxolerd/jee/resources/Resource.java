package io.github.anxolerd.jee.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Resource {
    @PersistenceContext
    @Produces
    EntityManager em;

    @Produces
    @RequestScoped
    public FacesContext producFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
