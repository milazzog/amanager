package com.mdev.amanager.web.controller;

import com.mdev.amanager.web.controller.base.RootManagedBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Objects;

/**
 * Created by gmilazzo on 28/09/2018.
 */
@ManagedBean
@ViewScoped
public class TestController extends RootManagedBean {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    private String username;

    @PostConstruct
    public void init(){
        username = Objects.nonNull(getLoggedInUser()) ? getLoggedInUser().getUsername() : "null";

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
