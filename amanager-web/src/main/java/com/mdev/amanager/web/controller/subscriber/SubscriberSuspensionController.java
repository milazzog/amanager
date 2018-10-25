package com.mdev.amanager.web.controller.subscriber;

import com.mdev.amanager.core.service.SubscriberService;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import com.mdev.amanager.core.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 17/10/2018.
 */
@ManagedBean
@ViewScoped
public class SubscriberSuspensionController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(SubscriberSuspensionController.class);

    private Subscriber subscriber;
    private SubscriberType type;
    private Date validFrom;

    @Autowired
    private SubscriberService subscriberService;

    @PostConstruct
    public void init() {

        validFrom = CommonUtil.roundToNearestMonth();
    }

    public void disable() {

        try {
            subscriber = subscriberService.disable(subscriber);
            messageManager.info("msg.info.subscriber.disable");
        } catch (ServiceException e) {
            logger.error(String.format("error while disabling %s", Subscriber.class.getSimpleName()), e);
            messageManager.error("msg.error.subscriber.disable");
        }
    }

    public void reactivate() {

        try {
            subscriber = subscriberService.reactivate(subscriber, type, validFrom);
            messageManager.info("msg.info.subscriber.reactivate");
        } catch (ServiceException e) {
            logger.error(String.format("error while reactivating %s", Subscriber.class.getSimpleName()), e);
            messageManager.error("msg.error.subscriber.reactivate");
        }
    }

    public String getHeader() {
        return Objects.isNull(subscriber) ? StringUtils.EMPTY : resourceBundle.getString("header.subscriber.disable.reactivate", subscriber.getFirstName(), subscriber.getLastName(), subscriber.getIdString());
    }

    public boolean isDisabled(Subscriber subscriber) {

        return Objects.isNull(subscriber) || (subscriber.isSuspended() && Objects.isNull(subscriber.getActiveCard()));
    }

    public boolean isCurrentDisabled() {

        return isDisabled(subscriber);
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public SubscriberType getType() {
        return type;
    }

    public void setType(SubscriberType type) {
        this.type = type;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public SubscriberType[] getSubscriberTypes() {

        return SubscriberType.values();
    }


}
