package com.mdev.amanager.web.controller.card;

import com.mdev.amanager.core.service.SubscriberCardService;
import com.mdev.amanager.core.service.SubscriberFeeService;
import com.mdev.amanager.core.service.model.SubscriberFeeCalculationResult;
import com.mdev.amanager.core.util.CommonUtil;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.model.SubscriberFee;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.*;

/**
 * Created by gmilazzo on 16/10/2018.
 */
@ManagedBean
@ViewScoped
public class SubscriberCardController extends RootManagedBean {

    private static final String NOT_ACTIVE = "not-active";
    private static final String ACTIVE = "active";
    private static final Logger logger = LogManager.getLogger(SubscriberCardController.class);

    @Autowired
    private SubscriberCardService subscriberCardService;

    @Autowired
    private SubscriberFeeService subscriberFeeService;

    private List<SubscriberCard> cards;
    private Map<String, List<SubscriberFee>> feeMap;
    private SubscriberFeeCalculationResult subscriberFeeCalculationResult;
    private SubscriberCard activeCard;

    private Subscriber subscriber;

    public String getHeader(SubscriberCard card) {

        return Objects.nonNull(card) ? resourceBundle.getString("header.subscriber.card.number", card.getCardNumber()) : StringUtils.EMPTY;
    }

    public String getTitle() {
        return Objects.nonNull(subscriber) ? resourceBundle.getString("header.subscriber.card.detail", subscriber.getFirstName(), subscriber.getLastName(), subscriber.getIdString()) : StringUtils.EMPTY;
    }

    public String getConfirmMessage() {

        return Objects.nonNull(subscriber) && Objects.nonNull(subscriberFeeCalculationResult) ? resourceBundle.getString("msg.info.subscriber.fee.add.confirm", CommonUtil.moneyStr(subscriberFeeCalculationResult.getAmount()), subscriber.getFirstName(), subscriber.getLastName(), subscriber.getIdString()) : StringUtils.EMPTY;
    }

    public String getStyle(SubscriberCard card) {

        if (Objects.nonNull(card)) {
            return Objects.nonNull(card.getDisabledAt()) ? NOT_ACTIVE : ACTIVE;
        }
        return StringUtils.EMPTY;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {

        this.subscriber = subscriber;
        refresh();
    }

    public void updateTotal() {

        try {
            if (Objects.nonNull(activeCard)) {
                subscriberFeeCalculationResult = subscriberFeeService.calculateAmount(subscriberFeeCalculationResult.getFrom(), subscriberFeeCalculationResult.getInterval(), activeCard.getType());
            }
        } catch (Exception e) {
            logger.error("error occurred while updating total: ", e);
        }
    }

    public void addFee() {

        try {
            if (Objects.nonNull(activeCard)) {

                subscriberCardService.addSubscriberFee(subscriberFeeCalculationResult, activeCard);
                refresh();
            }
        } catch (Exception e) {
            //TODO: GESTIRE
        }
    }

    public void refresh() {

        try {

            if (Objects.nonNull(subscriber) && CollectionUtils.isNotEmpty(subscriber.getCards())) {

                cards = new ArrayList<>(subscriber.getCards());
                cards.sort(Comparator.comparing(SubscriberCard::getId));
                Collections.reverse(cards);

                activeCard = cards.stream().filter(c -> Objects.isNull(c.getDisabledAt())).findFirst().orElse(null);

                if (Objects.nonNull(activeCard)) {
                    Date next = subscriberFeeService.getNextFeeDate(activeCard);
                    subscriberFeeCalculationResult = subscriberFeeService.calculateAmount(next, 1, activeCard.getType());
                }

                feeMap = new HashMap<>();

                for (SubscriberCard card : cards) {
                    ArrayList<SubscriberFee> fees = new ArrayList<>(card.getFees());
                    fees.sort(Comparator.comparing(SubscriberFee::getValidfrom));
                    feeMap.put(card.getCardNumber(), fees);
                }
            }
        } catch (Exception e) {
            //TODO: GESTIRE
        }
    }

    public List<SubscriberCard> getCards() {
        return cards;
    }

    public void setCards(List<SubscriberCard> cards) {
        this.cards = cards;
    }

    public List<SubscriberFee> getFees(SubscriberCard card) {

        return feeMap.get(card.getCardNumber());
    }

    public SubscriberFeeCalculationResult getSubscriberFeeCalculationResult() {
        return subscriberFeeCalculationResult;
    }

    public void setSubscriberFeeCalculationResult(SubscriberFeeCalculationResult subscriberFeeCalculationResult) {
        this.subscriberFeeCalculationResult = subscriberFeeCalculationResult;
    }
}
