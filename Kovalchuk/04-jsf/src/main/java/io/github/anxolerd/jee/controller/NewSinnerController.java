package io.github.anxolerd.jee.controller;


import io.github.anxolerd.jee.model.Sinner;
import io.github.anxolerd.jee.repository.SinnerRepo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Model
@Data
@Log4j2
public class NewSinnerController {
    private final SinnerRepo repo;
    private final FacesContext facesContext;
    @NotNull
    @Size(min = 1)
    String firstName;
    @NotNull
    @Size(min = 1)
    String lastName;
    @NotNull
    @Past
    Date birthDate;
    Date deathDate;

    @Inject
    public NewSinnerController(SinnerRepo repo, FacesContext facesContext) {
        this.repo = repo;
        this.facesContext = facesContext;
    }


    public void save() {
        Sinner sinner = new Sinner();
        sinner.setFirstName(firstName);
        sinner.setLastName(lastName);
        sinner.setBirthDate(birthDate);
        sinner.setDeathDate(deathDate);
        try {
            repo.create(sinner);
            String successMessage = "Saved! This sinner will pay for his/her sins!";
            log.info(successMessage);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successMessage, "Success")
            );
        } catch (Exception exc) {
            String errorMessage = "Oops! Cannot save sinner into database.";
            log.warn(errorMessage, exc);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error")
            );
        }
    }
}
