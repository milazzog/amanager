package com.mdev.amanager.web.controller.subscriber;

import com.mdev.amanager.core.datasource.SubscriberCardDataSource;
import com.mdev.amanager.core.datasource.SubscriberDataSource;
import com.mdev.amanager.core.service.VatCodeService;
import com.mdev.amanager.core.service.exceptions.ServiceException;
import com.mdev.amanager.persistence.domain.enums.Gender;
import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.core.service.SubscriberService;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import com.mdev.amanager.core.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Objects;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@ManagedBean
@ViewScoped
public class SubscriberAddController extends RootManagedBean{

    private static final Logger logger = LogManager.getLogger(SubscriberAddController.class);

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private VatCodeService vatCodeService;

    private Subscriber subscriber;

    private SubscriberDataSource subscriberDataSource;
    private SubscriberCardDataSource cardDataSource;

    @PostConstruct
    public void init(){

        subscriberDataSource = new SubscriberDataSource();
        cardDataSource = new SubscriberCardDataSource();
        cardDataSource.setValidFrom(CommonUtil.roundToNearestMonth());
    }

    public List<Municipality> autocompleteMunicipality(String part){

        return municipalityRepository.findByNamePattern(part);

    }

    public void generateVatCode(){

        if (StringUtils.isNotBlank(subscriberDataSource.getFirstName()) && StringUtils.isNotBlank(subscriberDataSource.getLastName()) && Objects.nonNull(subscriberDataSource.getGender()) && Objects.nonNull(subscriberDataSource.getBirthDate()) && Objects.nonNull(subscriberDataSource.getBirthDate())) {
            subscriberDataSource.setVatCode(vatCodeService.getVatCode(subscriberDataSource.getLastName(), subscriberDataSource.getFirstName(), subscriberDataSource.getBirthDate(), subscriberDataSource.getGender(), subscriberDataSource.getBirthCity()));
        }
    }

    public void register(){

        try {
            subscriberService.create(subscriberDataSource, cardDataSource);
            messageManager.info("msg.info.subscriber.registration");
            init();
        } catch (ServiceException e) {
            logger.error(String.format("error while creating %s", Subscriber.class.getSimpleName()), e);
            messageManager.error("msg.error.subscriber.registration");
        }
    }

    public void cancel(){
        this.subscriberDataSource = new SubscriberDataSource();
    }

    public IdentityDocumentType[] getIdentityDocumentTypes() {
        return IdentityDocumentType.values();
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    public SubscriberType[] getSubscriberTypes(){ return SubscriberType.values();}

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public SubscriberDataSource getSubscriberDataSource() {
        return subscriberDataSource;
    }

    public void setSubscriberDataSource(SubscriberDataSource subscriberDataSource) {
        this.subscriberDataSource = subscriberDataSource;
    }

    public SubscriberCardDataSource getCardDataSource() {
        return cardDataSource;
    }

    public void setCardDataSource(SubscriberCardDataSource cardDataSource) {
        this.cardDataSource = cardDataSource;
    }
}
