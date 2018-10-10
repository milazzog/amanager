package com.mdev.amanager.persistence.domain.repository.params.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gmilazzo on 05/10/2018.
 */
@ValueMatcher
public class DateMatcher{

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private String pattern;
    private Date from;
    private Date to;

    public DateMatcher() {
        this.pattern = DEFAULT_PATTERN;
    }

    public DateMatcher(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Date getFrom() {
        return from;
    }

    public DateMatcher setFrom(String from) throws ParseException {

        return setFrom((new SimpleDateFormat(pattern)).parse(from));
    }

    public DateMatcher setFrom(Date from) {
        this.from = from;
        return this;
    }

    public void setDateFrom(Date from) {
        this.from = from;
    }

    public Date getDateFrom() {
        return getFrom();
    }

    public Date getTo() {
        return to;
    }

    public DateMatcher setTo(String to) throws ParseException {

        return setTo((new SimpleDateFormat(pattern)).parse(to));
    }

    public DateMatcher setTo(Date to) {
        this.to = to;
        return this;
    }

    public void setDateTo(Date to) {
        this.to = to;
    }

    public Date getDateTo() {
        return getTo();
    }
}
