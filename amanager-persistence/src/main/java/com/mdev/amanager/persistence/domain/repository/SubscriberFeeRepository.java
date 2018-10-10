package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.model.SubscriberFee;
import com.mdev.amanager.persistence.domain.repository.base.Repository;

import java.util.List;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public interface SubscriberFeeRepository extends Repository<SubscriberFee> {

    List<SubscriberFee> findByCard(SubscriberCard card);

    List<SubscriberFee> findByCardNumber(String cardNumber);

    List<SubscriberFee> findBySubscriber(Subscriber subscriber);
}
