package com.mdev.amanager.web.service;

import com.mdev.amanager.web.resourcebundle.WebResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by gmilazzo on 07/10/2018.
 */
@Service
public class FacesMessageManager {

    @Autowired
    private WebResourceBundle resourceBundle;

    public void info(String messageKey) {
        info(null, messageKey);
    }

    public void info(String clientId, String messageKey, Object... messageParams) {
        addMessage(FacesMessage.SEVERITY_INFO, clientId, messageKey, messageParams);
    }

    public void warn(String messageKey) {
        warn(null, messageKey);
    }

    public void warn(String clientId, String messageKey, Object... messageParams) {
        addMessage(FacesMessage.SEVERITY_WARN, clientId, messageKey, messageParams);
    }

    public void error(String messageKey) {
        error(null, messageKey);
    }

    public void error(String clientId, String messageKey, Object... messageParams) {
        addMessage(FacesMessage.SEVERITY_ERROR, clientId, messageKey, messageParams);
    }

    public void fatal(String messageKey) {
        fatal(null, messageKey);
    }

    public void fatal(String clientId, String messageKey, Object... messageParams) {
        addMessage(FacesMessage.SEVERITY_FATAL, clientId, messageKey, messageParams);
    }

    public void addMessage(FacesMessage.Severity severity, String clientId, String messageKey, Object... messageParams) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(
                severity, resourceBundle.getString(messageKey, messageParams), null
        ));
    }
}