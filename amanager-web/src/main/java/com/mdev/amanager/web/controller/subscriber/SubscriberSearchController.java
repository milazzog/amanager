package com.mdev.amanager.web.controller.subscriber;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.persistence.domain.repository.SubscriberRepository;
import com.mdev.amanager.persistence.domain.repository.params.SubscriberSearchParam;
import com.mdev.amanager.core.service.SubscriberService;
import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@ManagedBean
@ViewScoped
public class SubscriberSearchController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(SubscriberSearchController.class);

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @ManagedProperty("#{subscriberDetailController}")
    private SubscriberDetailController subscriberDetailController;

    private SubscriberSearchParam searchParam;
    private List<Subscriber> searchResults;

    @PostConstruct
    public void init() {

        reset();
        search();
    }

    public void search() {

        searchResults = subscriberRepository.findBySearchParam(searchParam);
    }

    public void reset() {

        searchResults = null;
        searchParam = new SubscriberSearchParam();
    }

    public List<Municipality> autocompleteMunicipality(String part){

        return municipalityRepository.findByNamePattern(part);

    }

    public void loadSubscriberDetails(Subscriber subscriber) {

        subscriberDetailController.setSubscriber(subscriber);

    }

    public IdentityDocumentType[] getIdentityDocumentTypes() {
        return IdentityDocumentType.values();
    }

    public SubscriberSearchParam getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(SubscriberSearchParam searchParam) {
        this.searchParam = searchParam;
    }

    public List<Subscriber> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Subscriber> searchResults) {
        this.searchResults = searchResults;
    }

    public SubscriberDetailController getSubscriberDetailController() {
        return subscriberDetailController;
    }

    public void setSubscriberDetailController(SubscriberDetailController subscriberDetailController) {
        this.subscriberDetailController = subscriberDetailController;
    }
}
