package com.mdev.amanager.web.controller;

import com.mdev.amanager.core.datasource.SubscriberDataSource;
import com.mdev.amanager.core.service.VatCodeService;
import com.mdev.amanager.persistence.domain.enums.Gender;
import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.core.service.SubscriberService;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;
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

    private SubscriberDataSource dataSource;

    @PostConstruct
    public void init(){

        dataSource = new SubscriberDataSource();
    }

    public List<Municipality> autocompleteMunicipality(String part){

        return municipalityRepository.findByNamePattern(part);

    }

    public void generateVatCode(){

        if(StringUtils.isNotBlank(dataSource.getFirstName()) && StringUtils.isNotBlank(dataSource.getLastName()) && Objects.nonNull(dataSource.getGender()) && Objects.nonNull(dataSource.getBirthDate()) && Objects.nonNull(dataSource.getBirthDate())){
            dataSource.setVatCode(vatCodeService.getVatCode(dataSource.getLastName(), dataSource.getFirstName(), dataSource.getBirthDate(), dataSource.getGender(), dataSource.getBirthCity()));
        }
    }

    public void register(){

        try {
            subscriberService.create(dataSource);
            messageManager.info("msg.info.subscriber.registration");
        } catch (SubscriberService.SubscriberServiceException e) {
            logger.error(String.format("error while creating %s", Subscriber.class.getSimpleName()), e);
            messageManager.error("msg.error.subscriber.registration");
        }
    }

    public void cancel(){
        this.dataSource = new SubscriberDataSource();
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

    public SubscriberDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(SubscriberDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
