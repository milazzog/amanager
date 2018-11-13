package com.mdev.amanager.persistence.domain.util;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by gmilazzo on 07/11/2018.
 */
public class ReflectionUtil {

    public static <T extends Identifiable> T uppercaseAllString(T item) {
        Class<? extends Identifiable> c = item.getClass();

        for (Field f : c.getDeclaredFields()) {

            if (f.getType().isAssignableFrom(String.class)) {
                try {
                    f.setAccessible(true);
                    f.set(item, StringUtils.upperCase((String) f.get(item)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return item;
    }
}
