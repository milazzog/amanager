package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.repository.SubscriberRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import com.mdev.amanager.persistence.domain.repository.base.PredicateBuilder;
import com.mdev.amanager.persistence.domain.repository.params.SubscriberSearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Created by gmilazzo on 05/10/2018.
 */
@Repository
@Transactional
public class SubscriberRepositoryImpl extends BaseRepository<Subscriber> implements SubscriberRepository {

    @Override
    public List<Subscriber> findByRegistrationDate(Date registrationDate) {

        return this
                .named("subscriber.find.by.registration.date")
                .setParameter("registrationDate", registrationDate)
                .getResultList();
    }

    @Override
    public Subscriber findByVatCode(String vatCode) {
        return this
                .named("subscriber.find.by.vat.code")
                .setParameter("vatCode", vatCode)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Subscriber> findByVatCodePattern(String vatCode) {

        return this
                .named("subscriber.find.by.vat.code.pattern")
                .setParameter("vatCode", like(vatCode))
                .getResultList();
    }

    @Override
    public Subscriber findByDocument(IdentityDocumentType documentType, String documentNumber) {

        return this
                .named("subscriber.find.by.document")
                .setParameter("documentType", documentType)
                .setParameter("documentNumber", documentNumber)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Subscriber> findByDocumentPattern(IdentityDocumentType documentType, String documentNumber) {

        return this
                .named("subscriber.find.by.document.pattern")
                .setParameter("documentType", documentType)
                .setParameter("documentNumber", like(documentNumber))
                .getResultList();
    }

    @Override
    public List<Subscriber> findByFirstName(String firstName) {

        return this
                .named("subscriber.find.by.first.name")
                .setParameter("firstName", firstName)
                .getResultList();
    }

    @Override
    public List<Subscriber> findByFirstNamePattern(String firstName) {

        return this
                .named("subscriber.find.by.first.name.pattern")
                .setParameter("firstName", like(firstName))
                .getResultList();
    }

    @Override
    public List<Subscriber> findByLastName(String lastName) {

        return this
                .named("subscriber.find.by.last.name")
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<Subscriber> findByLastNamePattern(String lastName) {

        return this
                .named("subscriber.find.by.last.name.pattern")
                .setParameter("lastName", like(lastName))
                .getResultList();
    }

    @Override
    public List<Subscriber> findByFirstAndLastName(String firstName, String lastName) {

        return this
                .named("subscriber.find.by.first.and.last.name")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<Subscriber> findByFirstAndLastNamePattern(String firstName, String lastName) {

        return this
                .named("subscriber.find.by.first.and.last.name.pattern")
                .setParameter("firstName", like(firstName))
                .setParameter("lastName", like(lastName))
                .getResultList();
    }

    @Override
    public Subscriber findByPhone(String phone) {

        return this
                .named("subscriber.find.by.phone")
                .setParameter("phone", phone)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Subscriber> findByPhonePattern(String phone) {

        return this
                .named("subscriber.find.by.phone.pattern")
                .setParameter("phone", like(phone))
                .getResultList();
    }

    @Override
    public Subscriber findByEmail(String email) {

        return this
                .named("subscriber.find.by.email")
                .setParameter("email", email)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Subscriber> findByEmailPattern(String email) {

        return this
                .named("subscriber.find.by.email.pattern")
                .setParameter("email", like(email))
                .getResultList();
    }

    @Override
    public List<Subscriber> findByBirthCity(Municipality birthCity) {

        return this
                .named("subscriber.find.by.birth.city")
                .setParameter("birthCity", birthCity)
                .getResultList();
    }

    @Override
    public List<Subscriber> findByBirthDate(Date birthDate) {

        return this
                .named("subscriber.find.by.birth.date")
                .setParameter("birthDate", birthDate)
                .getResultList();
    }

    @Override
    public List<Subscriber> findByCity(Municipality city) {

        return this
                .named("subscriber.find.by.city")
                .setParameter("city", city)
                .getResultList();
    }

    @Override
    public Subscriber findByCardNumber(String cardNumber) {

        return this
                .named("subscriber.find.by.card.number")
                .setParameter("cardNumber", cardNumber)
                .setMaxResults(1)
                .getSingleResult();
    }

    public List<Subscriber> findBySearchParam(SubscriberSearchParam param) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subscriber> query = cb.createQuery(Subscriber.class);
        Root<Subscriber> ra = query.from(Subscriber.class);
        Join<Subscriber, SubscriberCard> t = StringMatcher.setted(param.getCardNumber()) ? ra.join("cards") : null;

        Predicate[] p = PredicateBuilder
                .getInstance(cb)
                .append(param.getId(), ra.get("id"))
                .append(param.getVatCode(), ra.get("vatCode"))
                .append(param.getDocumentNumber(), ra.get("documentNumber"))
                .append(param.getFirstName(), ra.get("firstName"))
                .append(param.getLastName(), ra.get("lastName"))
                .append(param.getPhone(), ra.get("phone"))
                .append(param.getEmail(), ra.get("email"))
                .append(param.getAddress(), ra.get("address"))
                .append(param.getCardNumber(), t, "cardNumber")
                .append(param.getRegistrationDate(), ra.get("registrationDate"))
                .append(param.getBirthDate(), ra.get("birthDate"))
                .append(param.getBirthCity(), ra.get("birthCity"))
                .append(param.getCity(), ra.get("city"))
                .append(param.getDocumentType(), ra.get("documentType"))
                .append(param.isSuspended(), ra.get("suspended"))
                .end();

        query.where(p);

        TypedQuery<Subscriber> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Class<Subscriber> getManagedClass() {
        return Subscriber.class;
    }
}
