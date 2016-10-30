package io.github.anxolerd.jee.controller;


import io.github.anxolerd.jee.model.Execution;
import io.github.anxolerd.jee.model.Sinner;
import io.github.anxolerd.jee.repository.ExecutionRepo;
import io.github.anxolerd.jee.repository.SinnerRepo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@Model
@Data
@Log4j2
public class NewExecutionController {
    final SinnerRepo sinnerRepo;
    final ExecutionRepo executionRepo;
    final FacesContext facesContext;
    String sinnerIdStr;
    @NotNull
    Sinner sinner;
    @NotNull
    @Size(min = 3)
    String description;
    @Valid
    String place;
    @Future
    Date date;
    @Inject
    public NewExecutionController(SinnerRepo sinnerRepo, ExecutionRepo executionRepo, FacesContext facesContext) {
        this.sinnerRepo = sinnerRepo;
        this.executionRepo = executionRepo;
        this.facesContext = facesContext;
    }

    public void initView() {
        log.debug("initialize new execution view");
        Long sinnerId = null;
        try {
            sinnerId = Long.parseLong(sinnerIdStr);
            sinner = sinnerRepo.findById(sinnerId);
            if (sinner == null) {
                throw new NullPointerException("Sinner is null");
            }
            Execution execution = sinner.getExecution();
            if (execution != null) {
                throw new IllegalStateException("There execution already exists");
            }
        } catch (IllegalStateException | NumberFormatException | NullPointerException exc) {
            log.warn(exc);
            ExternalContext ctx = facesContext.getExternalContext();
            ctx.setResponseStatus(SC_BAD_REQUEST);
            facesContext.responseComplete();
        }
    }

    public String save() {
        initView();
        ExternalContext ctx = facesContext.getExternalContext();
        ctx.getFlash().setKeepMessages(true);
        String action;

        Execution execution = new Execution();
        execution.setSinner(sinner);
        execution.setDate(date);
        execution.setDescription(description);
        execution.setPlace(place);


        try {
            sinner.setExecution(execution);
            executionRepo.create(execution);
            String successString = "Execution created";
            log.info(successString);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successString, "Saved")
            );
            action = String.format("/executions/new.xhtml?sinner_id=%s&faces-redirect=true", sinnerIdStr);
        } catch (Exception exc) {
            String errorString = "Failed plan an execution";
            log.warn(errorString, exc);
            facesContext.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, errorString, "Failed to save")
            );
            action = String.format("/executions/view.xhtml?id=%s&faces-redirect=true", execution.getId().toString());
        }
        return action;
    }
}
