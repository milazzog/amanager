package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;

import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 11/10/2018.
 */
public class SubscriberCardDataSource extends DataSource<SubscriberCard> {

    @Required
    private String cardNumber;
    @Required
    private SubscriberType type;
    @Required
    private Date createdAt;
    @Required
    private Date validFrom;
    @Required
    private Subscriber subscriber;

    private Date disabledAt;

    @Override
    protected SubscriberCard asData() {

        SubscriberCard c = new SubscriberCard();

        c.setCardNumber(getCardNumber());
        c.setType(getType());
        c.setCreatedAt(getCreatedAt());
        c.setValidFrom(getValidFrom());
        c.setSubscriber(getSubscriber());

        if (Objects.nonNull(getDisabledAt())) {
            c.setDisabledAt(getDisabledAt());
        }

        return c;
    }

    @Override
    public void fromData(SubscriberCard data) {

        if (Objects.nonNull(data)) {

            setCardNumber(data.getCardNumber());
            setType(data.getType());
            setCreatedAt(data.getCreatedAt());
            setValidFrom(data.getValidFrom());
            setSubscriber(data.getSubscriber());

            if (Objects.nonNull(data.getDisabledAt())) {
                setDisabledAt(data.getDisabledAt());
            }
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public SubscriberType getType() {
        return type;
    }

    public void setType(SubscriberType type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Date getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(Date disabledAt) {
        this.disabledAt = disabledAt;
    }
}
