package com.mdev.amanager.persistence.domain.repository.params;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.repository.params.base.DateMatcher;
import com.mdev.amanager.persistence.domain.repository.params.base.DatePattern;
import com.mdev.amanager.persistence.domain.repository.params.base.SearchParam;
import com.mdev.amanager.persistence.domain.repository.params.base.StringMatcher;

/**
 * Created by gmilazzo on 05/10/2018.
 */
public class SubscriberSearchParam extends SearchParam {

    private StringMatcher vatCode;
    private StringMatcher documentNumber;
    private StringMatcher firstName;
    private StringMatcher lastName;
    private StringMatcher phone;
    private StringMatcher email;
    private StringMatcher address;
    private StringMatcher cardNumber;

    private Long id;
    @DatePattern(pattern = "dd-MM-yyyy")
    private DateMatcher registrationDate;
    @DatePattern(pattern = "dd-MM-yyyy")
    private DateMatcher birthDate;
    private IdentityDocumentType documentType;
    private Municipality birthCity;
    private Municipality city;
    private Boolean suspended;

    public StringMatcher getVatCode() {
        return vatCode;
    }

    public void setVatCode(StringMatcher vatCode) {
        this.vatCode = vatCode;
    }

    public StringMatcher getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(StringMatcher documentNumber) {
        this.documentNumber = documentNumber;
    }

    public StringMatcher getFirstName() {
        return firstName;
    }

    public void setFirstName(StringMatcher firstName) {
        this.firstName = firstName;
    }

    public StringMatcher getLastName() {
        return lastName;
    }

    public void setLastName(StringMatcher lastName) {
        this.lastName = lastName;
    }

    public StringMatcher getPhone() {
        return phone;
    }

    public void setPhone(StringMatcher phone) {
        this.phone = phone;
    }

    public StringMatcher getEmail() {
        return email;
    }

    public void setEmail(StringMatcher email) {
        this.email = email;
    }

    public StringMatcher getAddress() {
        return address;
    }

    public void setAddress(StringMatcher address) {
        this.address = address;
    }

    public StringMatcher getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(StringMatcher cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateMatcher getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(DateMatcher registrationDate) {
        this.registrationDate = registrationDate;
    }

    public DateMatcher getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateMatcher birthDate) {
        this.birthDate = birthDate;
    }

    public IdentityDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(IdentityDocumentType documentType) {
        this.documentType = documentType;
    }

    public Municipality getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(Municipality birthCity) {
        this.birthCity = birthCity;
    }

    public Municipality getCity() {
        return city;
    }

    public void setCity(Municipality city) {
        this.city = city;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }
}
