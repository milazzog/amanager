package com.mdev.amanager.core.service;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Sequences;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
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

    @Transactional(rollbackFor = {RuntimeException.class, SubcscriberCardCreationException.class})
    public SubscriberCard create(Subscriber subscriber, SubscriberType type, Date validFrom) throws SubscriberCardServiceException {

        try {

            if(Objects.isNull(subscriber)){
                throw new IllegalArgumentException("subscriber is null.");
            }

            if(Objects.isNull(type)){
                throw new IllegalArgumentException("type is null.");
            }

            if(Objects.isNull(validFrom)){
                throw new IllegalArgumentException("validFrom is null.");
            }

            logger.info(String.format("searching for existing %s for subscriber [%s]...", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));

            Set<SubscriberCard> cards = subscriber.getCards();

            if(CollectionUtils.isEmpty(cards)){
                logger.info(String.format("no existing %s for subscriber [%s] found.", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));
            }else {
                logger.info(String.format("found %d existing %s for subscriber [%s].", cards.size(), SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));
                for(SubscriberCard c : cards){
                    if(Objects.isNull(c.getDisabledAt())){
                        logger.info(String.format("invalidating %s with card number [%s] for subscriber [%s] .", SubscriberCard.class.getSimpleName(), c.getCardNumber(), subscriber.getVatCode()));
                        c.setDisabledAt(new Date());
                        subscriberCardRepository.save(c);
                    }else {
                        logger.info(String.format("%s with card number [%s] is already invalid for subscriber [%s] .", SubscriberCard.class.getSimpleName(), c.getCardNumber(), subscriber.getVatCode()));
                    }
                }
            }

            logger.info(String.format("creating %s for subscriber [%s].", SubscriberCard.class.getSimpleName(), subscriber.getVatCode()));

            SubscriberCard sc = new SubscriberCard();
            sc.setType(type);
            sc.setCardNumber(generateCardNumber());
            sc.setCreatedAt(new Date());
            sc.setValidFrom(validFrom);
            sc.setSubscriber(subscriber);
            subscriberCardRepository.save(sc);

            logger.info(String.format("%s for subscriber [%s] successfully created with card number [%s] and id [%09d]", SubscriberCard.class.getSimpleName(), subscriber.getVatCode(), sc.getCardNumber(), sc.getId()));
            return sc;

        } catch (Exception e) {
            logger.error(String.format("error occurred while generating %s:", SubscriberCard.class.getSimpleName()), e);
            throw new SubcscriberCardCreationException(e);
        }
    }

    protected String generateCardNumber() {

        Long value = sequenceService.getNextValue(SUBSCRIBER_CARD);

        return StringUtils.leftPad(String.valueOf(value), SubscriberCard.CARD_NUMBER_LENGTH, '0');
    }

    public class SubscriberCardServiceException extends Exception {

        public SubscriberCardServiceException() {
            super();
        }

        public SubscriberCardServiceException(String message) {
            super(message);
        }

        public SubscriberCardServiceException(Throwable cause) {
            super(cause);
        }

        public SubscriberCardServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class SubcscriberCardCreationException extends SubscriberCardServiceException {

        public SubcscriberCardCreationException() {
            super();
        }

        public SubcscriberCardCreationException(String message) {
            super(message);
        }

        public SubcscriberCardCreationException(Throwable cause) {
            super(cause);
        }

        public SubcscriberCardCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
