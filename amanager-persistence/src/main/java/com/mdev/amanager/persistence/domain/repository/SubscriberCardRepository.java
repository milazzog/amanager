package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.repository.base.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public interface SubscriberCardRepository extends Repository<SubscriberCard> {

    SubscriberCard findByCardNumber(String cardNumber);
    List<SubscriberCard> findByCardNumberPattern(String cardNumber);
    List<SubscriberCard> findBySubscriberType(SubscriberType type);
    List<SubscriberCard> findByCreationDate(Date creationDate);
    List<SubscriberCard> findByCreationDateRange(Date from, Date to);
}
