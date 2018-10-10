package com.mdev.amanager.web.resourcebundle;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Created by gmilazzo on 07/10/2018.
 */
public abstract class ApplicationResourceBundle {

    public abstract ResourceBundle getLanguageBundle();

    public abstract String getString(String key);

    public final String getString(String key, Object... params) {
        return MessageFormat.format(getString(key), params);
    }
}

