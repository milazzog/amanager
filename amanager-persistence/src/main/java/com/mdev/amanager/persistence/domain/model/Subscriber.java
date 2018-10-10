package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.Gender;
import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import com.mdev.amanager.persistence.domain.model.base.Localizable;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@Entity
@Table(name = "SUBSCRIBER", uniqueConstraints = {
        @UniqueConstraint(name = "SUBSCRIBER_VAT_CODE_UNIQUE", columnNames = "VAT_CODE"),
        @UniqueConstraint(name = "SUBSCRIBER_DOCUMENT_UNIQUE", columnNames = {"DOCUMENT_TYPE", "DOCUMENT_NUMBER"}),
        @UniqueConstraint(name = "SUBSCRIBER_EMAIL_UNIQUE", columnNames = "EMAIL"),
        @UniqueConstraint(name = "SUBSCRIBER_PHONE_UNIQUE UNIQUE", columnNames = "PHONE")
})
@NamedQueries({
        @NamedQuery(name = "subscriber.find.by.registration.date", query = "from Subscriber s where s.registrationDate = :registrationDate"),
        @NamedQuery(name = "subscriber.find.by.vat.code", query = "from Subscriber s where s.vatCode = :vatCode"),
        @NamedQuery(name = "subscriber.find.by.vat.code.pattern", query = "from Subscriber s where s.vatCode like :vatCode"),
        @NamedQuery(name = "subscriber.find.by.document", query = "from Subscriber s where s.documentType = :documentType and s.documentNumber = :documentNumber"),
        @NamedQuery(name = "subscriber.find.by.document.pattern", query = "from Subscriber s where s.documentType = :documentType and s.documentNumber like :documentNumber"),
        @NamedQuery(name = "subscriber.find.by.first.name", query = "from Subscriber s where s.firstName = :firstName"),
        @NamedQuery(name = "subscriber.find.by.first.name.pattern", query = "from Subscriber s where s.firstName like :firstName"),
        @NamedQuery(name = "subscriber.find.by.last.name", query = "from Subscriber s where s.lastName = :lastName"),
        @NamedQuery(name = "subscriber.find.by.last.name.pattern", query = "from Subscriber s where s.lastName like :lastName"),
        @NamedQuery(name = "subscriber.find.by.first.and.last.name", query = "from Subscriber s where s.firstName = :firstName and s.lastName = :lastName"),
        @NamedQuery(name = "subscriber.find.by.first.and.last.name.pattern", query = "from Subscriber s where s.firstName like :firstName and s.lastName like :lastName"),
        @NamedQuery(name = "subscriber.find.by.phone", query = "from Subscriber s where s.phone = :phone"),
        @NamedQuery(name = "subscriber.find.by.phone.pattern", query = "from Subscriber s where s.phone like :phone"),
        @NamedQuery(name = "subscriber.find.by.email", query = "from Subscriber s where s.email = :email"),
        @NamedQuery(name = "subscriber.find.by.email.pattern", query = "from Subscriber s where s.email like :email"),
        @NamedQuery(name = "subscriber.find.by.birth.city", query = "from Subscriber s where s.birthCity = :birthCity"),
        @NamedQuery(name = "subscriber.find.by.birth.date", query = "from Subscriber s where s.birthDate = :birthDate"),
        @NamedQuery(name = "subscriber.find.by.city", query = "from Subscriber s where s.city = :city"),
        @NamedQuery(name = "subscriber.find.by.card.number", query = "from Subscriber s join s.cards c where c.cardNumber = :cardNumber")
})
public class Subscriber implements Identifiable, Localizable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @Column(name = "VAT_CODE", length = 16, nullable = false)
    private String vatCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", length = 32)
    private IdentityDocumentType documentType;

    @Column(name = "DOCUMENT_NUMBER", length = 32)
    private String documentNumber;

    @Column(name = "FIRST_NAME", length = 32, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 32, nullable = false)
    private String lastName;

    @Column(name = "PHONE", length = 32, nullable = false)
    private String phone;

    @Column(name = "EMAIL", length = 128)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "BIRTH_CITY", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_BIRTH_CITY"))
    private Municipality birthCity;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 1, nullable = false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CITY", foreignKey = @ForeignKey(name = "FK_SUBSCRIBER_CITY"))
    private Municipality city;

    @Column(name = "ADDRESS", length = 256, nullable = false)
    private String address;

    @Column(name = "SUSPENDED", nullable = false)
    private boolean suspended;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subscriber")
    private Set<SubscriberCard> cards = new HashSet<>();

    public SubscriberCard getActiveCard(){

        if(CollectionUtils.isNotEmpty(cards)){
            return cards.stream().filter(c -> Objects.isNull(c.getDisabledAt())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public Municipality getCity() {
        return city;
    }

    public void setCity(Municipality city) {
        this.city = city;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public Set<SubscriberCard> getCards() {
        return cards;
    }

    public void setCards(Set<SubscriberCard> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;

        Subscriber that = (Subscriber) o;

        if (suspended != that.suspended) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
            return false;
        if (vatCode != null ? !vatCode.equals(that.vatCode) : that.vatCode != null) return false;
        if (documentType != that.documentType) return false;
        if (documentNumber != null ? !documentNumber.equals(that.documentNumber) : that.documentNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (birthCity != null ? !birthCity.equals(that.birthCity) : that.birthCity != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (gender != that.gender) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (vatCode != null ? vatCode.hashCode() : 0);
        result = 31 * result + (documentType != null ? documentType.hashCode() : 0);
        result = 31 * result + (documentNumber != null ? documentNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthCity != null ? birthCity.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (suspended ? 1 : 0);
        return result;
    }
}
