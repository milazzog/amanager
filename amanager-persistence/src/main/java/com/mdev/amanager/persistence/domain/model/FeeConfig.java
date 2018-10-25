package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.enums.TimeInterval;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 22/10/2018.
 */
@Entity
@Table(name = "FEE_CONFIG")
@NamedQueries({
        @NamedQuery(name = "fee.config.find.by.type", query = "from FeeConfig fc where fc.type = :type and fc.removedAt is null")
})
public class FeeConfig implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 32, nullable = false)
    private SubscriberType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIME_INTERVAL", length = 32, nullable = false)
    private TimeInterval timeInterval;

    @Column(name = "AMOUNT", precision = 8, scale = 2, nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REMOVED_AT")
    private Date removedAt;

    @Override
    public Long getId() {
        return id;
    }

    public SubscriberType getType() {
        return type;
    }

    public void setType(SubscriberType type) {
        this.type = type;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Date getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(Date removedAt) {
        this.removedAt = removedAt;
    }
}
