package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.SubscriberCardDataSource;
import com.mdev.amanager.core.datasource.SubscriberDataSource;
import com.mdev.amanager.core.datasource.validation.MissingRequiredFieldException;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.repository.SubscriberRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Service
public class SubscriberService {

    private static final Logger logger = LogManager.getLogger(SubscriberService.class);

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberCardService subscriberCardService;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber create(SubscriberDataSource subscriberDataSource, SubscriberCardDataSource cardDataSource) throws ServiceException {

        try {

            subscriberDataSource.setRegistrationDate(new Date());
            subscriberDataSource.setSuspended(false);

            Subscriber s = subscriberDataSource.validate();

            logger.info(String.format("creating %s with first name [%s], last name [%s] and vat code [%s] of type [%s]", Subscriber.class.getSimpleName(), subscriberDataSource.getFirstName(), subscriberDataSource.getLastName(), subscriberDataSource.getVatCode(), cardDataSource.getType().name()));

            subscriberRepository.save(s);

            cardDataSource.setSubscriber(s);

            SubscriberCard c = subscriberCardService.create(cardDataSource);

            s.getCards().add(c);

            subscriberRepository.save(s);

            logger.info(String.format("%s with first name [%s], last name [%s] and vat code [%s] of type [%s] successfully created with id [%09d]", Subscriber.class.getSimpleName(), s.getFirstName(), s.getLastName(), s.getVatCode(), s.getActiveCard().getType().name(), s.getId()));

            return s;

        } catch (Exception e) {
            logger.error(String.format("error occurred while creating %s:", Subscriber.class.getSimpleName()), e);
            throw new ServiceException(e);
        }


    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber edit(Subscriber subscriber, SubscriberDataSource subscriberDataSource) throws ServiceException {

        if (Objects.isNull(subscriber)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }

        return edit(subscriber.getId(), subscriberDataSource);
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber edit(Long subscriberId, SubscriberDataSource subscriberDataSource) throws ServiceException {

        if (Objects.isNull(subscriberId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        if (Objects.isNull(subscriberDataSource)) {
            throw new ServiceException(String.format("can't edit a without %s as source.", SubscriberDataSource.class.getSimpleName()));
        }

        try {
            subscriberDataSource.validate();
        } catch (MissingRequiredFieldException e) {
            throw new ServiceException("invalid data source state:", e);
        }

        try {

            Subscriber subscriber = subscriberRepository.find(subscriberId);

            subscriber.setVatCode(subscriberDataSource.getVatCode());
            subscriber.setFirstName(subscriberDataSource.getFirstName());
            subscriber.setLastName(subscriberDataSource.getLastName());
            subscriber.setPhone(subscriberDataSource.getPhone());
            subscriber.setBirthCity(subscriberDataSource.getBirthCity());
            subscriber.setBirthDate(subscriberDataSource.getBirthDate());
            subscriber.setGender(subscriberDataSource.getGender());
            subscriber.setCity(subscriberDataSource.getCity());
            subscriber.setAddress(subscriberDataSource.getAddress());
            subscriber.setSuspended(subscriberDataSource.getSuspended());

            if (Objects.nonNull(subscriberDataSource.getDocumentType()) && StringUtils.isNotBlank(subscriberDataSource.getDocumentNumber())) {
                subscriber.setDocumentType(subscriberDataSource.getDocumentType());
                subscriber.setDocumentNumber(subscriberDataSource.getDocumentNumber());
            }

            if (StringUtils.isNotBlank(subscriberDataSource.getEmail())) {
                subscriber.setEmail(subscriberDataSource.getEmail());
            }

            return subscriberRepository.merge(subscriber);

        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while performing edit on %s with id [%09d]:", Subscriber.class.getSimpleName(), subscriberId), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber suspend(Subscriber subscriber) throws ServiceException {
        if (Objects.isNull(subscriber)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }
        return suspend(subscriber.getId());
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber suspend(Long subscriberId) throws ServiceException {

        if (Objects.isNull(subscriberId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        try {

            Subscriber subscriber = subscriberRepository.find(subscriberId);

            if (subscriber.isSuspended()) {
                throw new ServiceException(String.format("can't suspend %s with id [%09d]: already suspended.", Subscriber.class.getSimpleName(), subscriberId));
            }

            subscriber.setSuspended(true);
            subscriber = subscriberRepository.merge(subscriber);
            return subscriber;

        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while suspending %s with id [%09d]: ", Subscriber.class.getSimpleName(), subscriberId), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber disable(Subscriber subscriber) throws ServiceException {

        if (Objects.isNull(subscriber)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }

        return disable(subscriber.getId());
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber disable(Long subscriberId) throws ServiceException {

        if (Objects.isNull(subscriberId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        try {

            Subscriber s = suspend(subscriberId);

            SubscriberCard activeCard = s.getActiveCard();

            if (Objects.nonNull(activeCard)) {
                subscriberCardService.disable(activeCard);
            }

            return s;

        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while disabling %s with id [%09d]: ", Subscriber.class.getSimpleName(), subscriberId), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber unsuspend(Subscriber subscriber, SubscriberType type, Date validFrom) throws ServiceException {
        if (Objects.isNull(subscriber)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }
        return unsuspend(subscriber.getId());
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    private Subscriber unsuspend(Long subscriberId) throws ServiceException {

        if (Objects.isNull(subscriberId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        try {

            Subscriber subscriber = subscriberRepository.find(subscriberId);

            if (!subscriber.isSuspended()) {
                throw new ServiceException(String.format("can't unsuspend %s with id [%09d]: already suspended.", Subscriber.class.getSimpleName(), subscriberId));
            }

            subscriber.setSuspended(false);
            subscriber = subscriberRepository.merge(subscriber);
            return subscriber;

        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while unsuspending %s with id [%09d]: ", Subscriber.class.getSimpleName(), subscriberId), e);
        }
    }


    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber reactivate(Subscriber subscriber, SubscriberType type, Date validFrom) throws ServiceException {

        if (Objects.isNull(subscriber)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }

        return reactivate(subscriber.getId(), type, validFrom);
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Subscriber reactivate(Long subscriberId, SubscriberType type, Date validFrom) throws ServiceException {

        if (Objects.isNull(subscriberId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        try {

            Subscriber s = unsuspend(subscriberId);

            SubscriberCard activeCard = s.getActiveCard();

            if (Objects.nonNull(activeCard)) {
                subscriberCardService.disable(activeCard);
            }

            SubscriberCardDataSource cds = new SubscriberCardDataSource();

            cds.setValidFrom(validFrom);
            cds.setSubscriber(s);
            cds.setType(type);

            SubscriberCard sc = subscriberCardService.create(cds);

            s.getCards().add(sc);

            return subscriberRepository.merge(s);

        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while disabling %s with id [%09d]: ", Subscriber.class.getSimpleName(), subscriberId), e);
        }
    }


}
