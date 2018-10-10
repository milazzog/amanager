package org.mdev.amanager.test.repository;

import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.mdev.amanager.web.spring.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class MunicipalityRepositoryImplTest {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Test
    public void test_context_should_pass() {

        assertNotNull(municipalityRepository);
    }

    @Test
    public void find_all_provinces_should_pass(){
        List<Municipality> x = municipalityRepository.findAllProvinces();
        assertTrue(CollectionUtils.isNotEmpty(x));
        for(Municipality m : x){
            assertTrue(m.isChiefTown());
        }
    }

    @Test
    public void find_all_municipalies_should_pass(){
        List<Municipality> x = municipalityRepository.findAllMunicipalies();
        assertTrue(CollectionUtils.isNotEmpty(x));
        for(Municipality m : x){
            assertFalse(m.isChiefTown());
        }
    }

    @Test
    public void find_by_belfiorecode_should_pass(){
        Municipality x = municipalityRepository.findByBelfioreCode("C351");
        assertNotNull(x);
    }

    @Test
    public void find_by_name_should_pass(){
        List<Municipality> x = municipalityRepository.findByName("CATANIA");
        assertTrue(CollectionUtils.isNotEmpty(x));
    }

    @Test
    public void find_by_namepattern_should_pass(){
        List<Municipality> x = municipalityRepository.findByNamePattern("TANIA");
        assertTrue(CollectionUtils.isNotEmpty(x));
    }

    @Test
    public void find_by_province_should_pass(){
        List<Municipality> x = municipalityRepository.findByProvince("CT");
        assertTrue(CollectionUtils.isNotEmpty(x));
    }

    @Test
    public void find_by_zip_should_pass(){
        List<Municipality> x = municipalityRepository.findByZip("95047");
        assertTrue(CollectionUtils.isNotEmpty(x));
    }

    @Test
    public void find_by_zippattern_should_pass(){
        List<Municipality> x = municipalityRepository.findByZipPattern("047");
        assertTrue(CollectionUtils.isNotEmpty(x));
    }
}
