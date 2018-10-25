package com.mdev.amanager.core.datasource;

import com.mdev.amanager.core.datasource.validation.DataSource;
import com.mdev.amanager.core.datasource.validation.Required;
import com.mdev.amanager.persistence.domain.enums.Gender;
import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Created by gmilazzo on 07/10/2018.
 */
public class SubscriberDataSource extends DataSource<Subscriber> {

    @Required
    private Date registrationDate;
    @Required
    private String vatCode;
    @Required
    private String firstName;
    @Required
    private String lastName;
    @Required
    private String phone;
    @Required
    private Municipality birthCity;
    @Required
    private Date birthDate;
    @Required
    private Gender gender;
    @Required
    private Municipality city;
    @Required
    private String address;
    @Required
    private Boolean suspended;

    private IdentityDocumentType documentType;
    private String documentNumber;
    private String email;

    @Override
    protected Subscriber asData() {

        Subscriber s = new Subscriber();

        s.setRegistrationDate(getRegistrationDate());
        s.setVatCode(getVatCode());
        s.setFirstName(getFirstName());
        s.setLastName(getLastName());
        s.setPhone(getPhone());
        s.setBirthCity(getBirthCity());
        s.setBirthDate(getBirthDate());
        s.setGender(getGender());
        s.setCity(getCity());
        s.setAddress(getAddress());
        s.setSuspended(getSuspended());

        if (Objects.nonNull(getDocumentType()) && StringUtils.isNotBlank(getDocumentNumber())) {
            s.setDocumentType(getDocumentType());
            s.setDocumentNumber(getDocumentNumber());
        }

        if (StringUtils.isNotBlank(getEmail())) {
            s.setEmail(getEmail());
        }

        return s;
    }

    @Override
    public void fromData(Subscriber data) {

        if (Objects.nonNull(data)) {

            setRegistrationDate(data.getRegistrationDate());
            setVatCode(data.getVatCode());
            setFirstName(data.getFirstName());
            setLastName(data.getLastName());
            setPhone(data.getPhone());
            setBirthCity(data.getBirthCity());
            setBirthDate(data.getBirthDate());
            setGender(data.getGender());
            setCity(data.getCity());
            setAddress(data.getAddress());
            setSuspended(data.isSuspended());

            if (Objects.nonNull(data.getDocumentType()) && StringUtils.isNotBlank(data.getDocumentNumber())) {
                setDocumentType(data.getDocumentType());
                setDocumentNumber(data.getDocumentNumber());
            }

            if (StringUtils.isNotBlank(data.getEmail())) {
                setEmail(data.getEmail());
            }
        }
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Municipality getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(Municipality birthCity) {
        this.birthCity = birthCity;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Municipality getCity() {
        return city;
    }

    public void setCity(Municipality city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public IdentityDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(IdentityDocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
