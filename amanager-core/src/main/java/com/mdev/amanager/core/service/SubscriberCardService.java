package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.SubscriberCardDataSource;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.service.model.SubscriberFeeCalculationResult;
import com.mdev.amanager.persistence.domain.model.Sequences;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.model.SubscriberFee;
import com.mdev.amanager.persistence.domain.repository.SubscriberCardRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Service
public class SubscriberCardService implements Sequences {

    private static final Logger logger = LogManager.getLogger(SubscriberCardService.class);

    @Autowired
    private SubscriberCardRepository subscriberCardRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SubscriberFeeService subscriberFeeService;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public SubscriberCard create(SubscriberCardDataSource subscriberCardDataSource) throws ServiceException {

        try {

            subscriberCardDataSource.setCardNumber(generateCardNumber());
            subscriberCardDataSource.setCreatedAt(new Date());

            Subscriber subscriber = subscriberCardDataSource.getSubscriber();
            SubscriberCard sc = subscriberCardDataSource.validate();

            logger.info(String.format("searching for existing %s for subscriber [%s]...", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));

            Set<SubscriberCard> cards = subscriber.getCards();

            if (CollectionUtils.isEmpty(cards)) {
                logger.info(String.format("no existing %s for subscriber [%s] found.", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));
            } else {
                logger.info(String.format("found %d existing %s for subscriber [%s].", cards.size(), SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));
                for (SubscriberCard c : cards) {
                    if (Objects.isNull(c.getDisabledAt())) {
                        logger.info(String.format("invalidating %s with card number [%s] for subscriber [%s] .", SubscriberCard.class.getSimpleName(), c.getCardNumber(), subscriber.getVatCode()));
                        c.setDisabledAt(new Date());
                        subscriberCardRepository.save(c);
                    } else {
                        logger.info(String.format("%s with card number [%s] is already invalid for subscriber [%s] .", SubscriberCard.class.getSimpleName(), c.getCardNumber(), subscriber.getVatCode()));
                    }
                }
            }

            logger.info(String.format("creating %s for subscriber [%s].", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));

            subscriberCardRepository.save(sc);

            logger.info(String.format("%s for subscriber [%s] successfully created with card number [%s] and id [%09d]", SubscriberCard.class.getSimpleName(), subscriber.getVatCode(), sc.getCardNumber(), sc.getId()));
            return sc;

        } catch (Exception e) {
            logger.error(String.format("error occurred while generating %s:", SubscriberCard.class.getSimpleName()), e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public SubscriberCard disable(SubscriberCard subscriberCard) throws ServiceException {
        if (Objects.isNull(subscriberCard)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }
        return disable(subscriberCard.getId());
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public SubscriberCard disable(Long subscriberCardId) throws ServiceException {
        if (Objects.isNull(subscriberCardId)) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }
        try {
            SubscriberCard subscriberCard = subscriberCardRepository.find(subscriberCardId);
            if (Objects.nonNull(subscriberCard.getDisabledAt())) {
                throw new ServiceException(String.format("can't disable %s with id [%09d]: already disabled.", SubscriberCard.class.getSimpleName(), subscriberCardId));
            }
            subscriberCard.setDisabledAt(new Date());
            return subscriberCardRepository.merge(subscriberCard);
        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while disabling %s with id [%09d]", SubscriberCard.class.getSimpleName(), subscriberCardId), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public SubscriberCard addSubscriberFee(SubscriberFeeCalculationResult calculationResult, SubscriberCard subscriberCard) throws ServiceException {
        try {
            SubscriberFee fee = subscriberFeeService.create(subscriberCard, calculationResult);
            subscriberCard.getFees().add(fee);
            return subscriberCardRepository.merge(subscriberCard);
        } catch (Exception e) {
            throw new ServiceException(String.format("error occurred while adding %s", SubscriberFee.class.getSimpleName()), e);
        }
    }

    protected String generateCardNumber() {

        Long value = sequenceService.getNextValue(SUBSCRIBER_CARD);

        return StringUtils.leftPad(String.valueOf(value), SubscriberCard.CARD_NUMBER_LENGTH, '0');
    }

}
