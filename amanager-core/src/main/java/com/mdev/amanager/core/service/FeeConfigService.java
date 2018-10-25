package com.mdev.amanager.core.service;

import com.mdev.amanager.persistence.domain.enums.SubscriberType;
import com.mdev.amanager.persistence.domain.model.FeeConfig;
import com.mdev.amanager.persistence.domain.model.Subscriber;
import com.mdev.amanager.persistence.domain.repository.FeeConfigRepository;
import com.mdev.amanager.persistence.domain.repository.exceptions.EntityPersistenceException;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by gmilazzo on 24/10/2018.
 */
@Service
@DependsOn("feeConfigRepository")
public class FeeConfigService {

    private static final Logger logger = LogManager.getLogger(FeeConfigService.class);

    private Map<SubscriberType, FeeConfig> feeConfigMap;

    @Autowired
    private FeeConfigRepository feeConfigRepository;

    @PostConstruct
    public void init() {

        refresh();
    }

    public void refresh() {

        logger.info("refreshing feeConfigMap.");

        feeConfigMap = new HashMap<>();

        for (SubscriberType type : SubscriberType.values()) {
            try {

                FeeConfig feeConfig = feeConfigRepository.findByType(type);

                if (Objects.nonNull(feeConfig)) {
                    feeConfigMap.put(type, feeConfig);
                }

            } catch (EntityPersistenceException e) {
                logger.error(String.format("error while retrieving [%s] for [%s] : %s.", FeeConfig.class.getSimpleName(), SubscriberType.class.getSimpleName(), type.name()), e);
            }
        }

        if (MapUtils.isEmpty(feeConfigMap)) {
            throw new RuntimeException("feeConfigMap is empty, can't start application.");
        }

        if (MapUtils.size(feeConfigMap) != SubscriberType.values().length) {
            throw new RuntimeException(String.format("feeConfigMap has invalid size {expected : %d - got : %d}, can't start application.", SubscriberType.values().length, MapUtils.size(feeConfigMap)));
        }

        logger.info(String.format("refresh complete, feeConfigMap contains %d keys.", MapUtils.size(feeConfigMap)));
    }

    public FeeConfig get(SubscriberType type) {

        return feeConfigMap.get(type);
    }
}
