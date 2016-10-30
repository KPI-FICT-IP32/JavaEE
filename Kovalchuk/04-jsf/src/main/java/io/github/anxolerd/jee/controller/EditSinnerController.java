package io.github.anxolerd.jee.controller;

import io.github.anxolerd.jee.model.Sinner;
import io.github.anxolerd.jee.repository.SinnerRepo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Log4j2
@Model
@Data
public class EditSinnerController {
    final SinnerRepo repo;
    final FacesContext facesContext;

    @NotNull
    String sinnerIdStr;

    Sinner sinner;
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
    public EditSinnerController(SinnerRepo repo, FacesContext facesContext) {
        this.repo = repo;
        this.facesContext = facesContext;
    }


    private void getEntities() {
        // TODO: find a better way
        log.debug("initialze sinner update view");
        Long sinnerId = null;
        try {
            sinnerId = Long.parseLong(sinnerIdStr);
            sinner = repo.findById(sinnerId);
            if (sinner == null) {
                throw new NullPointerException("Sinner is null");
            }
        } catch (NumberFormatException | NullPointerException exc) {
            ExternalContext ctx = facesContext.getExternalContext();
            ctx.setResponseStatus(SC_NOT_FOUND);
            facesContext.responseComplete();
        }
    }

    public void initView() {
        getEntities();
        populateParams();
    }

    private void populateParams() {
        lastName = sinner.getLastName();
        firstName = sinner.getFirstName();
        birthDate = sinner.getBirthDate();
        deathDate = sinner.getDeathDate();
    }

    public String save() {
        // FIXME: redirects do not work properly
        getEntities();
        ExternalContext ctx = facesContext.getExternalContext();
        ctx.getFlash().setKeepMessages(true);
        String action;

        sinner.setFirstName(firstName);
        sinner.setLastName(lastName);
        sinner.setBirthDate(birthDate);
        sinner.setDeathDate(deathDate);
        try {
            repo.update(sinner);
            String successMessage = "Updated! This sinner will pay for his/her sins!";
            log.info(successMessage);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successMessage, "Success")
            );
            action = String.format("/sinners/view.xhtml?id=%d&faces-redirect=true", sinner.getId());
        } catch (Exception exc) {
            String errorMessage = "Oops! Cannot update sinner info.";
            log.warn(errorMessage, exc);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error")
            );
            action = String.format("/sinners/update.xhtml?id=%s&faces-redirect=true", sinner.getId().toString());
        }
        return action;
    }
}
