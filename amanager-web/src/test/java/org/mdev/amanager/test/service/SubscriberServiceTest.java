package org.mdev.amanager.test.service;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.core.service.SubscriberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.mdev.amanager.web.spring.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class SubscriberServiceTest {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Test
    public void test_context_should_pass() {

        assertNotNull(subscriberService);
    }

    @Test
    public void test_create_should_pass() throws ParseException, SubscriberService.SubscriberServiceException {

        SubscriberType subscriberType = SubscriberType.SUPPORTER;
        String vatCode = "MLZGPP88L05G371c";
        String firstName = "Giuseppe";
        String lastName = "Milazzo";
        String phone = "3248042911";
        Municipality birthCity = municipalityRepository.findByBelfioreCode("G371");
        Date birthDate = (new SimpleDateFormat("dd-MM-yyyy")).parse("05-07-1988");
        Municipality city = municipalityRepository.findByBelfioreCode("G371");
        String address = "Vico G. Pulvirenti n. 9";
        Date validFrom = (new SimpleDateFormat("dd-MM-yyyy")).parse("06-10-2018");
        IdentityDocumentType documentType = null;
        String documentNumber = null;
        String email = null;

        //Subscriber s = subscriberService.create(subscriberType, vatCode, firstName, lastName, phone, birthCity, birthDate, city, address, validFrom, documentType, documentNumber, email);

//        assertNotNull(s);
//        assertNotNull(s.getCards());
//        assertEquals(1, s.getCards().size());
//        assertEquals("00000002", s.getCards().iterator().next().getCardNumber());
    }
}
