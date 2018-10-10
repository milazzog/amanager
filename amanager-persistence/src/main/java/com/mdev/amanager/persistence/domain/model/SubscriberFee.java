package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@Entity
@Table(name = "SUBSCRIBER_FEE")
@NamedQueries({
        @NamedQuery(name = "subscriber.fee.find.by.card", query = "from SubscriberFee sf where sf.card = :card"),
        @NamedQuery(name = "subscriber.fee.find.by.card.number", query = "from SubscriberFee sf join sf.card c where c.cardNumber = :cardNumber"),
        @NamedQuery(name = "subscriber.fee.find.by.subscriber", query = "from SubscriberFee sf join sf.card c join c.subscriber s where s = :subscriber")
})
public class SubscriberFee implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CARD", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_FEE_CARD"))
    private SubscriberCard card;

    @Column(name = "AMOUNT", precision = 8, scale = 2, nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_FROM", nullable = false)
    private Date validfrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_TO", nullable = false)
    private Date validTo;

    @Override
    public Long getId() {
        return id;
    }

    public SubscriberCard getCard() {
        return card;
    }

    public void setCard(SubscriberCard card) {
        this.card = card;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriberFee)) return false;

        SubscriberFee that = (SubscriberFee) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (validfrom != null ? !validfrom.equals(that.validfrom) : that.validfrom != null) return false;
        return validTo != null ? validTo.equals(that.validTo) : that.validTo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (validfrom != null ? validfrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
