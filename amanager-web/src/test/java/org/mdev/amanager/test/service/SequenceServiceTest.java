package org.mdev.amanager.test.service;

import com.mdev.amanager.core.service.SequenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.mdev.amanager.web.spring.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class SequenceServiceTest {

    @Autowired
    private SequenceService sequenceService;

    @Test
    public void test_context_should_pass() {

        assertNotNull(sequenceService);
    }

    @Test
    public void test_new_value_should_pass(){

        Long v = sequenceService.getNextValue("TEST");

        assertTrue(v == 1L);
    }

    @Test
    public void test_new_value_update_should_pass(){

        Long v1 = sequenceService.getNextValue("TEST");

        assertTrue(v1 == 1L);

        Long v2 = sequenceService.getNextValue("TEST");

        assertTrue(v2 == 2L);
    }


}
