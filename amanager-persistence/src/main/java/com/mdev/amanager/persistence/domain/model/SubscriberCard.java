package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@Entity
@Table(name = "SUBSCRIBER_CARD", uniqueConstraints = {
        @UniqueConstraint(name = "SUBSCRIBER_CARD_CARD_NUMBER_UNIQUE", columnNames = "CARD_NUMBER")
})
@NamedQueries({
        @NamedQuery(name = "subscriber.card.find.by.card.number", query = "from SubscriberCard sc where sc.cardNumber = :cardNumber"),
        @NamedQuery(name = "subscriber.card.find.by.card.number.pattern", query = "from SubscriberCard sc where sc.cardNumber like :cardNumber"),
        @NamedQuery(name = "subscriber.card.find.by.subscriber.type", query = "from SubscriberCard sc where sc.type = :type"),
        @NamedQuery(name = "subscriber.card.find.by.creation.date", query = "from SubscriberCard sc where sc.createdAt = :createdAt"),
})
public class SubscriberCard implements Identifiable{

    public static final int CARD_NUMBER_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CARD_NUMBER", length = CARD_NUMBER_LENGTH, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 32, nullable = false)
    private SubscriberType type;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "DISABLED_AT")
    private Date disabledAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBSCRIBER", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_CARD_SUBSCRIBER"))
    private Subscriber subscriber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "card")
    private Set<SubscriberFee> fees = new HashSet<>();

    @Override
    public Long getId() {
        return id;
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

    public Date getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(Date disabledAt) {
        this.disabledAt = disabledAt;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Set<SubscriberFee> getFees() {
        return fees;
    }

    public void setFees(Set<SubscriberFee> fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriberCard)) return false;

        SubscriberCard that = (SubscriberCard) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (type != that.type) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        return disabledAt != null ? disabledAt.equals(that.disabledAt) : that.disabledAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (disabledAt != null ? disabledAt.hashCode() : 0);
        return result;
    }
}
