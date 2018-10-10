package com.mdev.amanager.persistence.domain.repository;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.base.Repository;
import com.mdev.amanager.persistence.domain.repository.params.SubscriberSearchParam;

import java.util.Date;
import java.util.List;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public interface SubscriberRepository extends Repository<Subscriber> {

    List<Subscriber> findByRegistrationDate(Date registrationDate);

    Subscriber findByVatCode(String vatCode);

    List<Subscriber> findByVatCodePattern(String vatCode);

    Subscriber findByDocument(IdentityDocumentType documentType, String documentNumber);

    List<Subscriber> findByDocumentPattern(IdentityDocumentType documentType, String documentNumber);

    List<Subscriber> findByFirstName(String firstName);

    List<Subscriber> findByFirstNamePattern(String firstName);

    List<Subscriber> findByLastName(String lastName);

    List<Subscriber> findByLastNamePattern(String lastName);

    List<Subscriber> findByFirstAndLastName(String firstName, String lastName);

    List<Subscriber> findByFirstAndLastNamePattern(String firstName, String lastName);

    Subscriber findByPhone(String phone);

    List<Subscriber> findByPhonePattern(String phone);

    Subscriber findByEmail(String email);

    List<Subscriber> findByEmailPattern(String email);

    List<Subscriber> findByBirthCity(Municipality birthCity);

    List<Subscriber> findByBirthDate(Date birthDate);

    List<Subscriber> findByCity(Municipality city);

    Subscriber findByCardNumber(String cardNumber);

    List<Subscriber> findBySearchParam(SubscriberSearchParam param);
}
