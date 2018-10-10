package com.mdev.amanager.core.service;

import com.mdev.amanager.persistence.domain.model.Sequence;
import com.mdev.amanager.persistence.domain.repository.SequenceRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Service
public class SequenceService {

    private static final Logger logger = LogManager.getLogger(SequenceService.class);

    @Autowired
    private SequenceRepository sequenceRepository;

    @Transactional(rollbackFor = Exception.class)
    public synchronized Long getNextValue(String name) {

        Sequence s = null;

        try {
            s = sequenceRepository.findByName(name);
        }catch (EntityNotFoundException e){
            logger.info(String.format("no %s with name [%s]... generating new sequence.", Sequence.class.getSimpleName(), name));
        }

        if (Objects.isNull(s)) {
            s = new Sequence();
            s.setName(name);
            s.setValue(0L);
        }

        s.setValue(s.getValue() + 1L);
        sequenceRepository.save(s);
        logger.info(String.format("saved %s with id [%09d], name [%s] and value [%s].", Sequence.class.getSimpleName(), s.getId(), s.getName(), s.getValue()));

        return s.getValue();
    }
}
