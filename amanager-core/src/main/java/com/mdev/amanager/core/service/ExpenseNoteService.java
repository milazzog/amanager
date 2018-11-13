package com.mdev.amanager.core.service;

import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.util.CommonUtil;
import com.mdev.amanager.core.util.ServiceMessages;
import com.mdev.amanager.persistence.domain.enums.CreditCausal;
import com.mdev.amanager.persistence.domain.model.CreditNote;
import com.mdev.amanager.persistence.domain.model.ExpenseNote;
import com.mdev.amanager.persistence.domain.model.RawProductRegistry;
import com.mdev.amanager.persistence.domain.repository.ExpenseNoteRepository;
import com.mdev.amanager.persistence.domain.repository.RawProductRegistryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@Service
public class ExpenseNoteService {

    private static final Logger logger = LogManager.getLogger(ExpenseNoteService.class);

    @Autowired
    private ExpenseNoteRepository expenseNoteRepository;

    @Autowired
    private CreditNoteService creditNoteService;

    @Autowired
    private RawProductRegistryService rawProductRegistryService;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public ExpenseNote create(BigDecimal amount, CreditCausal causal, String notes) throws ServiceException {

        try {
            CreditNote cn = creditNoteService.create(amount.negate(), causal, notes);

            ExpenseNote en = new ExpenseNote();
            en.setCreatedAt(new Date());
            en.setCreditNote(cn);

            expenseNoteRepository.save(en);

            logger.info(String.format("%s successfully created with id %s", ExpenseNote.class.getSimpleName(), en.getIdString()));

            return en;

        } catch (Exception e) {
            logger.error(String.format("error while creating %s: ", ExpenseNote.class.getSimpleName()), e);
            throw new ServiceException(String.format("error while creating %s: ", ExpenseNote.class.getSimpleName()), e);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public ExpenseNote create(Collection<? extends RawProductRegistry> productRegistries) throws ServiceException {

        if (CollectionUtils.isEmpty(productRegistries)) {
            throw new ServiceException(String.format("can't create %s: no related %s [empty collection].", ExpenseNote.class.getSimpleName(), RawProductRegistry.class.getSimpleName()));
        }

        try {

            logger.info(String.format("creating %s for: ", ExpenseNote.class.getSimpleName()));

            productRegistries
                    .forEach(p ->
                            logger.info(
                                    String.format("%-10s | %s | %3d | %5s",
                                            CommonUtil.reduce(p.getRawProduct().getName(), "...", 10),
                                            p.getRawProduct().getIdString(),
                                            CommonUtil.toInt(p.getQuantity()),
                                            CommonUtil.moneyStr(p.getAmount()))));


            BigDecimal amount = productRegistries
                    .stream()
                    .map(RawProductRegistry::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            logger.info(String.format("total is: %s", CommonUtil.moneyStr(amount)));

            CreditNote cn = creditNoteService.create(amount.negate(), CreditCausal.SUPPLYING);
            ExpenseNote en = new ExpenseNote();

            en.setCreditNote(cn);
            en.setCreatedAt(new Date());

            expenseNoteRepository.save(en);

            for (RawProductRegistry p : productRegistries) {
                logger.info(String.format("adding %s to %s with id %s",
                        String.format("%-10s | %s | %3d | %5s",
                                CommonUtil.reduce(p.getRawProduct().getName(), "...", 10),
                                p.getRawProduct().getIdString(),
                                CommonUtil.toInt(p.getQuantity()),
                                CommonUtil.moneyStr(p.getAmount())),
                        ExpenseNote.class.getSimpleName(), en.getIdString()));
                p.setExpenseNote(en);
                en.getRawProductRegistries().add(rawProductRegistryService.create(p));
            }

            en = expenseNoteRepository.merge(en);

            logger.info(ServiceMessages.createSuccess(en));

            return en;

        } catch (Exception e) {
            logger.error(String.format("error while creating %s: ", ExpenseNote.class.getSimpleName()), e);
            throw new ServiceException(String.format("error while creating %s: ", ExpenseNote.class.getSimpleName()), e);
        }
    }

}
