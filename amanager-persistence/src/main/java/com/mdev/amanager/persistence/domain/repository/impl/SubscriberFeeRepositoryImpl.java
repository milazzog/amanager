package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.model.SubscriberFee;
import com.mdev.amanager.persistence.domain.repository.SubscriberFeeRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Repository
@Transactional
public class SubscriberFeeRepositoryImpl extends BaseRepository<SubscriberFee> implements SubscriberFeeRepository {

    @Override
    public List<SubscriberFee> findByCard(SubscriberCard card) {

        return this
                .named("subscriber.fee.find.by.card")
                .setParameter("card", card)
                .getResultList();
    }

    @Override
    public List<SubscriberFee> findByCardNumber(String cardNumber) {

        return this
                .named("subscriber.fee.find.by.card.number")
                .setParameter("cardNumber", cardNumber)
                .getResultList();
    }

    @Override
    public List<SubscriberFee> findBySubscriber(Subscriber subscriber) {

        return this
                .named("subscriber.fee.find.by.subscriber")
                .setParameter("subscriber", subscriber)
                .getResultList();
    }

    @Override
    public Class<SubscriberFee> getManagedClass() {
        return SubscriberFee.class;
    }
}
