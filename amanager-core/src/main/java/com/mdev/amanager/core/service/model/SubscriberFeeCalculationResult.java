package com.mdev.amanager.core.service.model;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.FeeConfig;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 24/10/2018.
 */
public class SubscriberFeeCalculationResult {

    private Date from;
    private Date to;
    private Integer interval;
    private SubscriberType type;
    private BigDecimal amount;
    private FeeConfig feeConfig;

    public SubscriberFeeCalculationResult(Date from, Date to, Integer interval, SubscriberType type, BigDecimal amount, FeeConfig feeConfig) {
        this.from = from;
        this.to = to;
        this.interval = interval;
        this.type = type;
        this.amount = amount;
        this.feeConfig = feeConfig;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public SubscriberType getType() {
        return type;
    }

    public void setType(SubscriberType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FeeConfig getFeeConfig() {
        return feeConfig;
    }

    public void setFeeConfig(FeeConfig feeConfig) {
        this.feeConfig = feeConfig;
    }
}
