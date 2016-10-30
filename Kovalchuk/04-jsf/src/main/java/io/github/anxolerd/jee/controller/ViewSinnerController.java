package io.github.anxolerd.jee.controller;

import io.github.anxolerd.jee.model.Accuse;
import io.github.anxolerd.jee.model.Sinner;
import io.github.anxolerd.jee.repository.AccuseRepo;
import io.github.anxolerd.jee.repository.SinnerRepo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Log4j2
@Model
@Data
public class ViewSinnerController {
    final SinnerRepo sinnerRepo;
    final AccuseRepo accuseRepo;
    final FacesContext facesContext;

    @NotNull
    String sinnerIdStr;

    @NotNull
    @Size(min = 3)
    String accuses;

    Sinner sinner;

    @Inject
    public ViewSinnerController(SinnerRepo sinnerRepo, AccuseRepo accuseRepo, FacesContext facesContext) {
        this.sinnerRepo = sinnerRepo;
        this.accuseRepo = accuseRepo;
        this.facesContext = facesContext;
    }

    public void initView() {
        // TODO: find a better way
        log.debug("initialze sinner view");
        Long sinnerId = null;
        try {
            sinnerId = Long.parseLong(sinnerIdStr);
            sinner = sinnerRepo.findById(sinnerId);
            if (sinner == null) {
                throw new NullPointerException("Sinner is null");
            }
        } catch (NumberFormatException | NullPointerException exc) {
            ExternalContext ctx = facesContext.getExternalContext();
            ctx.setResponseStatus(SC_NOT_FOUND);
            facesContext.responseComplete();
        }
    }

    public String addAccuse() {
        initView();
        ExternalContext ctx = facesContext.getExternalContext();
        ctx.getFlash().setKeepMessages(true);

        Accuse accuse = new Accuse();
        accuse.setAccuses(accuses);
        accuse.setSinner(sinner);
        try {
            sinner.getAccuses().add(accuse);
            accuseRepo.create(accuse);
            String successString = "Accused successfully";

            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successString, "Saved")
            );
        } catch (Exception exc) {
            String errorString = "Failed to accuse sinner";
            log.warn(errorString, exc);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, errorString, "Failed to save")
            );
        }
        return String.format("/sinners/view.xhtml?id=%s&faces-redirect=true", sinnerIdStr);
    }
}
