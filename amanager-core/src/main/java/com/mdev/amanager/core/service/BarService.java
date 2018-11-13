package com.mdev.amanager.core.service;

import com.mdev.amanager.core.service.base.Refreshable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Service
public class BarService implements Refreshable {

    private static final Logger logger = LogManager.getLogger(BarService.class);

    @Autowired
    private ProductService productService;


    @Override
    public void refresh() {

        logger.info("refresh");
    }
}
