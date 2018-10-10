package org.mdev.amanager.test.repository;

import com.mdev.amanager.persistence.domain.enums.IdentityDocumentType;
import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import com.mdev.amanager.persistence.domain.repository.SubscriberRepository;
import com.mdev.amanager.persistence.domain.repository.params.SubscriberSearchParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mdev.amanager.test.util.TestUtil;
import com.mdev.amanager.web.spring.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gmilazzo on 05/10/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class SubscriberRepositoryImplTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Test
    public void test_context_should_pass() {

        assertNotNull(subscriberRepository);
        assertNotNull(municipalityRepository);
    }

    @Test
    public void test_save_should_pass() {

        List<Subscriber> ss = initData();

        for (Subscriber s : ss) {
            subscriberRepository.save(s);
        }
    }

    @Test
    public void find_by_registration_date_should_pass() {

        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByRegistrationDate(TestUtil.date("01-01-2018"));
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_vat_code_should_pass() {
        test_save_should_pass();
        Subscriber s = subscriberRepository.findByVatCode("MLZGPP88L05G371K");
        assertNotNull(s);
    }

    @Test
    public void find_by_vat_code_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByVatCodePattern("MLZ");
        assertNotNull(s);
        assertTrue(s.size() == 2);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_document_should_pass() {
        test_save_should_pass();
        Subscriber s = subscriberRepository.findByDocument(IdentityDocumentType.IDENTITY_CARD, "AK995844");
        assertNotNull(s);
    }

    @Test
    public void find_by_document_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByDocumentPattern(IdentityDocumentType.IDENTITY_CARD, "AK");
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_first_name_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByFirstName("Giuseppe");
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_first_name_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByFirstNamePattern("G");
        assertNotNull(s);
        assertTrue(s.size() == 2);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_last_name_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByLastName("MAZZAGLIA");
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_last_name_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByLastNamePattern("Mil");
        assertNotNull(s);
        assertTrue(s.size() == 2);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_first_and_last_name_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByFirstAndLastName("Giuseppe", "Milazzo");
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_first_and_last_name_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByFirstAndLastNamePattern("Giusep", "Mila");
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_phone_should_pass() {
        test_save_should_pass();
        Subscriber s = subscriberRepository.findByPhone("3248042910");
        assertNotNull(s);
    }

    @Test
    public void find_by_phone_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByPhonePattern("3");
        assertNotNull(s);
        assertTrue(s.size() == 3);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_email_should_pass() {
        test_save_should_pass();
        Subscriber s = subscriberRepository.findByEmail("milazzo.ga@gmail.com");
        assertNotNull(s);
    }

    @Test
    public void find_by_email_pattern_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByEmailPattern("@");
        assertNotNull(s);
        assertTrue(s.size() == 3);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_birth_city_should_pass() {
        test_save_should_pass();
        Municipality c1 = municipalityRepository.findByBelfioreCode("G371");
        List<Subscriber> s = subscriberRepository.findByBirthCity(c1);
        assertNotNull(s);
        assertTrue(s.size() == 2);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_birth_date_should_pass() {
        test_save_should_pass();
        List<Subscriber> s = subscriberRepository.findByBirthDate(TestUtil.date("05-07-1988"));
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_city_should_pass() {
        test_save_should_pass();
        Municipality c2 = municipalityRepository.findByBelfioreCode("C351");
        List<Subscriber> s = subscriberRepository.findByCity(c2);
        assertNotNull(s);
        assertTrue(s.size() == 1);
        assertNotNull(s.iterator().next());
    }

    @Test
    public void find_by_card_number_should_pass() {

        //TODO: to be implemented
    }

    @Test
    public void find_by_search_param_should_pass() throws ParseException {
        test_save_should_pass();
        SubscriberSearchParam p = new SubscriberSearchParam();
        p.getBirthDate().setFrom("05-07-1988").setTo("05-07-1988");
        List<Subscriber> s = subscriberRepository.findBySearchParam(p);
        assertNotNull(s);
        assertEquals(1, s.size());
        assertNotNull(s.iterator().next());

    }

    public List<Subscriber> initData() {

        Municipality c1 = municipalityRepository.findByBelfioreCode("G371");
        Municipality c2 = municipalityRepository.findByBelfioreCode("C351");

        Subscriber s1 = new Subscriber();
        Subscriber s2 = new Subscriber();
        Subscriber s3 = new Subscriber();

        s1.setRegistrationDate(TestUtil.date("01-01-2018"));
        s1.setVatCode("MLZGPP88L05G371K");
        s1.setDocumentType(IdentityDocumentType.IDENTITY_CARD);
        s1.setDocumentNumber("AK995844");
        s1.setFirstName("Giuseppe");
        s1.setLastName("Milazzo");
        s1.setPhone("3248042910");
        s1.setEmail("milazzo.ga@gmail.com");
        s1.setBirthCity(c1);
        s1.setBirthDate(TestUtil.date("05-07-1988"));
        s1.setCity(c1);
        s1.setAddress("Vico G. Pulvirenti 9");
        s1.setSuspended(false);

        s2.setRegistrationDate(TestUtil.date("15-03-2018"));
        s2.setVatCode("MZZNNN59H56C351L");
        s2.setDocumentType(null);
        s2.setDocumentNumber(null);
        s2.setFirstName("Agata");
        s2.setLastName("Mazzaglia");
        s2.setPhone("3402530251");
        s2.setEmail("mazzagliagata@hotmail.it");
        s2.setBirthCity(c2);
        s2.setBirthDate(TestUtil.date("19-06-1959"));
        s2.setCity(c1);
        s2.setAddress("Via Lombardia 9");
        s2.setSuspended(false);

        s3.setRegistrationDate(TestUtil.date("25-06-2018"));
        s3.setVatCode("MLZSVT58L15G371C");
        s3.setDocumentType(null);
        s3.setDocumentNumber(null);
        s3.setFirstName("Salvatore");
        s3.setLastName("Milazzo");
        s3.setPhone("3472340047");
        s3.setEmail("milazzo.sal@hotmail.it");
        s3.setBirthCity(c1);
        s3.setBirthDate(TestUtil.date("15-07-1958"));
        s3.setCity(c2);
        s3.setAddress("Via Pulvirenti 9");
        s3.setSuspended(false);

        return Arrays.asList(s1, s2, s3);
    }
}
