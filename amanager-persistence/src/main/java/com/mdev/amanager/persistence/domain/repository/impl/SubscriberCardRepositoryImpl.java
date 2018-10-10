package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.repository.SubscriberCardRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.base.PredicateBuilder;
import com.mdev.amanager.persistence.domain.repository.params.base.DateMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Repository
@Transactional
public class SubscriberCardRepositoryImpl extends BaseRepository<SubscriberCard> implements SubscriberCardRepository {

    @Override
    public SubscriberCard findByCardNumber(String cardNumber) {

        return this
                .named("subscriber.card.find.by.card.number")
                .setParameter("cardNumber", cardNumber)
                .setMaxResults(1)
                .getSingleResult();

    }

    @Override
    public List<SubscriberCard> findByCardNumberPattern(String cardNumber) {

        return this
                .named("subscriber.card.find.by.card.number.pattern")
                .setParameter("cardNumber", like(cardNumber))
                .getResultList();

    }

    @Override
    public List<SubscriberCard> findBySubscriberType(SubscriberType type) {

        return this
                .named("subscriber.card.find.by.subscriber.type")
                .setParameter("type", type)
                .getResultList();

    }

    @Override
    public List<SubscriberCard> findByCreationDate(Date creationDate) {

        return this
                .named("subscriber.card.find.by.creation.date")
                .setParameter("createdAt", creationDate)
                .getResultList();

    }

    @Override
    public List<SubscriberCard> findByCreationDateRange(Date from, Date to) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SubscriberCard> query = cb.createQuery(SubscriberCard.class);
        Root<SubscriberCard> ra = query.from(SubscriberCard.class);

        Predicate[] p = PredicateBuilder
                .getInstance(em.getCriteriaBuilder())
                .append((new DateMatcher()).setFrom(from).setTo(to), ra.get("createdAt"))
                .end();

        query.where(p);

        TypedQuery<SubscriberCard> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Class<SubscriberCard> getManagedClass() {
        return SubscriberCard.class;
    }
}
