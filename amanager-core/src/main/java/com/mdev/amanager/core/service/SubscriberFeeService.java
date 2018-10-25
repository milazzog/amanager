package com.mdev.amanager.core.service;

import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.service.model.CreditNoteService;
import com.mdev.amanager.core.service.model.SubscriberFeeCalculationResult;
import com.mdev.amanager.core.util.CommonUtil;
import com.mdev.amanager.persistence.domain.enums.CreditCausal;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.CreditNote;
import com.mdev.amanager.persistence.domain.model.FeeConfig;
import com.mdev.amanager.persistence.domain.model.SubscriberCard;
import com.mdev.amanager.persistence.domain.model.SubscriberFee;
import com.mdev.amanager.persistence.domain.repository.SubscriberFeeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 24/10/2018.
 */
@Service
public class SubscriberFeeService {

    private static final Logger logger = LogManager.getLogger(SubscriberFeeService.class);

    @Autowired
    private SubscriberFeeRepository subscriberFeeRepository;

    @Autowired
    private CreditNoteService creditNoteService;

    @Autowired
    private FeeConfigService feeConfigService;

    public SubscriberFeeCalculationResult calculateAmount(Date from, int counter, SubscriberType type) throws ServiceException {

        if (Objects.isNull(from)) {
            throw new ServiceException(ServiceException.nullParam("from"));
        }

        if (Objects.isNull(type)) {
            throw new ServiceException(ServiceException.nullParam("type"));
        }

        logger.info(String.format("retrieving active %s for %s [%s]...", FeeConfig.class.getSimpleName(), SubscriberType.class.getSimpleName(), type.name()));

        FeeConfig feeConfig = feeConfigService.get(type);

        logger.info(String.format("%s successfully retrieved.", FeeConfig.class.getSimpleName()));
        logger.info(String.format("calculating amount for %s [%s] with date [%s], interval [%d] and fee rate [%s]...",
                SubscriberType.class.getSimpleName(), type.name(), CommonUtil.dateStr(from), counter, CommonUtil.moneyStr(feeConfig.getAmount())));

        Date end = DateUtils.addDays(feeConfig.getTimeInterval().addInterval(from, counter), -1);
        BigDecimal amount = CommonUtil.money(feeConfig.getAmount().multiply(BigDecimal.valueOf(counter)));

        logger.info(String.format("calculation performed amount is [%s].", CommonUtil.moneyStr(amount)));

        return new SubscriberFeeCalculationResult(from, end, counter, type, amount, feeConfig);
    }

    public Date getNextFeeDate(SubscriberCard subscriberCard) throws ServiceException {

        if (Objects.isNull(subscriberCard)) {
            throw new ServiceException(ServiceException.nullParam("subscriberCard"));
        }

        logger.info(String.format("extracting next %s date for %s with id [%s]...", SubscriberFee.class.getSimpleName(), SubscriberCard.class.getSimpleName(), subscriberCard.getIdString()));

        Date next;

        if (CollectionUtils.isEmpty(subscriberCard.getFees())) {
            next = CommonUtil.roundToNearestMonth();
            logger.info(String.format("no existing %s for %s with id [%s].", SubscriberFee.class.getSimpleName(), SubscriberCard.class.getSimpleName(), subscriberCard.getIdString()));
        } else {
            next = DateUtils.addDays(
                    subscriberCard
                            .getFees()
                            .stream()
                            .map(SubscriberFee::getValidTo)
                            .max(Date::compareTo).orElseThrow(() -> new ServiceException("can't extract max.")),
                    1);
        }

        logger.info(String.format("next date is [%s].", CommonUtil.dateStr(next)));
        return next;
    }


    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public SubscriberFee create(SubscriberCard subscriberCard, SubscriberFeeCalculationResult calculationResult) throws ServiceException {

        if (Objects.isNull(calculationResult)) {
            throw new ServiceException(ServiceException.nullParam("calculationResult"));
        }

        if (Objects.isNull(subscriberCard)) {
            throw new ServiceException(ServiceException.NULL_OBJECT);
        }

        if (Objects.isNull(subscriberCard.getId())) {
            throw new ServiceException(ServiceException.DETACHED_INSTANCE);
        }

        CreditNote creditNote = creditNoteService.create(calculationResult.getAmount(), CreditCausal.SUBSCRIBER_FEE);
        SubscriberFee fee = new SubscriberFee();

        fee.setCard(subscriberCard);
        fee.setFeeConfig(calculationResult.getFeeConfig());
        fee.setValidfrom(calculationResult.getFrom());
        fee.setValidTo(calculationResult.getTo());
        fee.setCreditNote(creditNote);

        subscriberFeeRepository.save(fee);

        return fee;
    }

}
