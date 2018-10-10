package com.mdev.amanager.persistence.domain.repository.base;

import com.mdev.amanager.persistence.domain.repository.params.base.DateMatcher;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by gmilazzo on 05/10/2018.
 */
public class PredicateBuilder {

    private List<Predicate> predicates;
    private CriteriaBuilder cb;

    public static PredicateBuilder getInstance(CriteriaBuilder cb) {

        return new PredicateBuilder(cb);
    }

    private PredicateBuilder(CriteriaBuilder cb) {
        this.predicates = new ArrayList<>();
        this.cb = cb;
    }


    @SuppressWarnings("unchecked")
    public PredicateBuilder append(DateMatcher date, Path path) {

        if(Objects.isNull(date)){
            return this;
        }

        Date from = date.getFrom();
        Date to = date.getTo();

        if (Objects.nonNull(from) && Objects.nonNull(to)) {

            predicates.add(cb.between(path, from, to));
        }

        if (Objects.nonNull(from)) {

            predicates.add(cb.greaterThanOrEqualTo(path, from));
        }

        if (Objects.nonNull(to)) {

            predicates.add(cb.lessThanOrEqualTo(path, to));
        }

        return this;
    }

    public PredicateBuilder append(String to, CriteriaBuilder cb, Path path) {

        if (StringUtils.isNotBlank(to)) {

            predicates.add(cb.equal(path, to));
        }
        return this;
    }

    public PredicateBuilder append(Object to, Path path) {

        if (Objects.nonNull(to)) {

            predicates.add(cb.equal(path, to));
        }
        return this;
    }

    public <Z, X> PredicateBuilder append(StringMatcher value, From<Z, X> from, String field){

        if(Objects.nonNull(from) && StringMatcher.setted(value) && StringUtils.isNotBlank(field)){
            append(value, from.get(field));
        }
        return this;
    }

    public PredicateBuilder append(StringMatcher value, Path<String> path) {
        if (Objects.nonNull(value) && StringUtils.isNotBlank(value.getValue()) && Objects.nonNull(value.getMode())) {
            switch (value.getMode()) {
                case EXACT:
                    predicates.add(cb.equal(path, value.getValue()));
                    break;
                case ENDS:
                    predicates.add(cb.like(path, "%" + value.getValue().toLowerCase()));
                    break;
                case STARTS:
                    predicates.add(cb.like(path, value.getValue().toLowerCase() + "%"));
                    break;
                case ANYWHERE:
                    predicates.add(cb.like(path, "%" + value.getValue().toLowerCase() + "%"));
                    break;
            }
        }
        return this;
    }

    public Predicate[] end() {

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
