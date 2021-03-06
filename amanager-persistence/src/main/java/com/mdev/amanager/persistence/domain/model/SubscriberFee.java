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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "FEE_CONFIG", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_CARD_FEE_CONFIG"))
    private FeeConfig feeConfig;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREDIT_NOTE", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_CARD_CREDIT_NOTE"))
    private CreditNote creditNote;

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

    public FeeConfig getFeeConfig() {
        return feeConfig;
    }

    public void setFeeConfig(FeeConfig feeConfig) {
        this.feeConfig = feeConfig;
    }

    public CreditNote getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(CreditNote creditNote) {
        this.creditNote = creditNote;
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
        if (feeConfig != null ? !feeConfig.equals(that.feeConfig) : that.feeConfig != null) return false;
        if (creditNote != null ? !creditNote.equals(that.creditNote) : that.creditNote != null) return false;
        if (validfrom != null ? !validfrom.equals(that.validfrom) : that.validfrom != null) return false;
        return validTo != null ? validTo.equals(that.validTo) : that.validTo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (feeConfig != null ? feeConfig.hashCode() : 0);
        result = 31 * result + (creditNote != null ? creditNote.hashCode() : 0);
        result = 31 * result + (validfrom != null ? validfrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
