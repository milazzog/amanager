package com.mdev.amanager.web.controller.subscriber;

import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.commons.lang3.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Objects;

/**
 * Created by gmilazzo on 10/10/2018.
 */
@ManagedBean
@ViewScoped
public class SubscriberDetailController extends RootManagedBean {

    private Subscriber subscriber;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getHeader() {
        if(Objects.nonNull(subscriber)) {
            return resourceBundle.getString("header.subscriber.detail", subscriber.getFirstName(), subscriber.getLastName(), subscriber.getIdString());
        }
        return StringUtils.EMPTY;
    }
}
