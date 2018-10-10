package com.mdev.amanager.core.service;

import com.mdev.amanager.core.datasource.SubscriberDataSource;
import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Municipality;
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

    @Autowired
    private VatCodeService vatCodeService;

    @Transactional(rollbackFor = {RuntimeException.class, SubscriberServiceException.class})
    public Subscriber create(SubscriberDataSource dataSource) throws SubscriberServiceException {

        try {

            String vatCode = vatCodeService.getVatCode(dataSource.getLastName(), dataSource.getFirstName(), dataSource.getBirthDate(), dataSource.getGender(), dataSource.getBirthCity());
            dataSource.setVatCode(vatCode);

            dataSource.validate();

            Subscriber s = new Subscriber();

            logger.info(String.format("creating %s with first name [%s], last name [%s] and vat code [%s] of type [%s]", Subscriber.class.getSimpleName(), dataSource.getFirstName(), dataSource.getLastName(), vatCode, dataSource.getSubscriberType().name()));

            s.setRegistrationDate(new Date());
            s.setVatCode(dataSource.getVatCode());
            s.setFirstName(dataSource.getFirstName());
            s.setLastName(dataSource.getLastName());
            s.setPhone(dataSource.getPhone());
            s.setBirthCity(dataSource.getBirthCity());
            s.setBirthDate(dataSource.getBirthDate());
            s.setGender(dataSource.getGender());
            s.setCity(dataSource.getCity());
            s.setAddress(dataSource.getAddress());
            s.setSuspended(false);

            if (Objects.nonNull(dataSource.getDocumentType()) && StringUtils.isNotBlank(dataSource.getDocumentNumber())) {
                s.setDocumentType(dataSource.getDocumentType());
                s.setDocumentNumber(dataSource.getDocumentNumber());
            }

            if (StringUtils.isNotBlank(dataSource.getEmail())) {
                s.setEmail(dataSource.getEmail());
            }

            subscriberRepository.save(s);

            SubscriberCard c = subscriberCardService.create(s, dataSource.getSubscriberType(), dataSource.getValidFrom());

            s.getCards().add(c);

            subscriberRepository.save(s);

            logger.info(String.format("%s with first name [%s], last name [%s] and vat code [%s] of type [%s] successfully created with id [%09d]", Subscriber.class.getSimpleName(), s.getFirstName(), s.getLastName(), s.getVatCode(), dataSource.getSubscriberType().name(), s.getId()));

            return s;

        } catch (Exception e) {
            logger.error(String.format("error occurred while creating %s:", Subscriber.class.getSimpleName()), e);
            throw new SubcscriberCreationException(e);
        }


    }


    public class SubscriberServiceException extends Exception {

        public SubscriberServiceException() {
            super();
        }

        public SubscriberServiceException(String message) {
            super(message);
        }

        public SubscriberServiceException(Throwable cause) {
            super(cause);
        }

        public SubscriberServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class SubcscriberCreationException extends SubscriberServiceException {

        public SubcscriberCreationException() {
            super();
        }

        public SubcscriberCreationException(String message) {
            super(message);
        }

        public SubcscriberCreationException(Throwable cause) {
            super(cause);
        }

        public SubcscriberCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
