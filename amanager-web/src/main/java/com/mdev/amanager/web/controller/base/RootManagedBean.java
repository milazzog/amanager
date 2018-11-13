package com.mdev.amanager.web.controller.base;

import com.mdev.amanager.persistence.domain.model.User;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import com.mdev.amanager.persistence.domain.model.base.Localizable;
import com.mdev.amanager.web.model.UserDetailsImpl;
import com.mdev.amanager.web.resourcebundle.WebResourceBundle;
import com.mdev.amanager.web.service.FacesMessageManager;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public class RootManagedBean extends SpringBeanAutowiringSupport {

    private static final String ERROR_PARAM_NAME = "isError";
    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

    @Autowired
    protected FacesMessageManager messageManager;

    @Autowired
    protected WebResourceBundle resourceBundle;

    public String id(Identifiable obj) {
        return Objects.nonNull(obj) ? obj.getIdString() : "-";
    }

    public String getDate(Date date) {

        if (Objects.nonNull(date))
            return (new SimpleDateFormat(DATE_PATTERN)).format(date);
        return "-";

    }

    public String getTime(Date date) {

        if (Objects.nonNull(date))
            return (new SimpleDateFormat(TIME_PATTERN)).format(date);
        return "-";
    }

    public String getDateTime(Date date) {
        if (Objects.nonNull(date))
            return (new SimpleDateFormat(DATETIME_PATTERN)).format(date);
        return "-";
    }

    public String reduce(String val) {

        if (StringUtils.isBlank(val)) {
            return "-";
        }

        if (val.length() > 20) {
            return val.substring(0, 20) + " ...";
        }

        return val;
    }

    public String getLocalization(Localizable obj) {

        return String.format("%s - %s - %s - (%s)", obj.getAddress(), obj.getCity().getZip(), obj.getCity().getName(), obj.getCity().getProvince());
    }

    public String getLocalizationReduced(Localizable obj) {

        return reduce(getLocalization(obj));
    }

    public User getLoggedInUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getUser();
        }
        return null;
    }

    protected void markError() {
        RequestContext.getCurrentInstance().addCallbackParam(ERROR_PARAM_NAME, true);
    }

    protected void redirect(String outcome) {

        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        String url = contextPath + outcome;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("error while loading page: %s", url), e);
        }
    }
}
