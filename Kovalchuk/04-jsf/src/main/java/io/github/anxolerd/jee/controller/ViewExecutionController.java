package io.github.anxolerd.jee.controller;


import io.github.anxolerd.jee.model.Execution;
import io.github.anxolerd.jee.repository.ExecutionRepo;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.Model;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Data
@Model
@Log4j2
public class ViewExecutionController {
    final ExecutionRepo repo;
    final FacesContext facesContext;

    @Inject
    public ViewExecutionController(ExecutionRepo repo, FacesContext facesContext) {
        this.repo = repo;
        this.facesContext = facesContext;
    }

    String executionIdStr;
    Execution execution;

    public void initView() {
        // TODO: find a better way
        log.debug("initialze execution view");
        Long sinnerId = null;
        try {
            sinnerId = Long.parseLong(executionIdStr);
            execution = repo.findById(sinnerId);
            if (execution == null) {
                throw new NullPointerException("Execution is null");
            }
        } catch (NumberFormatException | NullPointerException exc) {
            log.warn("Bad request to execution view", exc);
            ExternalContext ctx = facesContext.getExternalContext();
            ctx.setResponseStatus(SC_NOT_FOUND);
            facesContext.responseComplete();
        }
    }
}
