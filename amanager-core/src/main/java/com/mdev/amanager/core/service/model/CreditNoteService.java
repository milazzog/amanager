package com.mdev.amanager.core.service.model;

import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.core.util.CommonUtil;
import com.mdev.amanager.persistence.domain.enums.CreditCausal;
import com.mdev.amanager.persistence.domain.model.CreditNote;
import com.mdev.amanager.persistence.domain.repository.CreditNoteRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gmilazzo on 24/10/2018.
 */
@Service
public class CreditNoteService {

    private static final Logger logger = LogManager.getLogger(CreditNoteService.class);

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public CreditNote create(BigDecimal amount, CreditCausal causal) {

        return create(amount, causal, StringUtils.EMPTY);
    }


    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public CreditNote create(BigDecimal amount, CreditCausal causal, String notes) {

        logger.info(String.format("creating %s with causal [%s] and amount [%s].", CreditNote.class.getSimpleName(), causal.name(), CommonUtil.money(amount).toString()));

        CreditNote creditNote = new CreditNote();
        creditNote.setAddedAt(new Date());
        creditNote.setAmount(amount);
        creditNote.setCausal(causal);

        if (StringUtils.isNotBlank(notes)) {
            creditNote.setNotes(notes);
        }

        creditNoteRepository.save(creditNote);

        return creditNote;
    }
}
