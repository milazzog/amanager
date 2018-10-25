package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.function.BiFunction;

/**
 * Created by gmilazzo on 22/10/2018.
 */
public enum TimeInterval implements Labeled {

    MONTHLY("time.interval.monthly", "time.interval.monthly.time.unit", DateUtils::addMonths),
    ANNUAL("time.interval.annual", "time.interval.annual.time.unit", DateUtils::addYears);

    private String labelKey;
    private String timeUnitLabel;
    private BiFunction<Date, Integer, Date> fcn;

    TimeInterval(String labelKey, String timeUnitLabel, BiFunction<Date, Integer, Date> fcn) {
        this.labelKey = labelKey;
        this.timeUnitLabel = timeUnitLabel;
        this.fcn = fcn;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

    public String getTimeUnitLabel() {
        return timeUnitLabel;
    }

    public Date addInterval(Date date, int amount) {

        return fcn.apply(date, amount);
    }
}
