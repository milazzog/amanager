package com.mdev.amanager.core.datasource.validation;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gmilazzo on 07/10/2018.
 */
public abstract class DataSource<T> {

    protected abstract T asData();

    public abstract void fromData(T data);

    public T validate() throws MissingRequiredFieldException {
        Class<?> c = this.getClass();

        List<Field> fields = Stream
                .of(c.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Required.class))
                .collect(Collectors.toList());

        for (Field f : fields) {
            try {
                f.setAccessible(true);

                if (f.getType().isAssignableFrom(String.class)) {
                    if (StringUtils.isBlank((String) f.get(this))) {
                        throw new MissingRequiredFieldException(String.format("field %s is blank", f.getName()));
                    }
                } else if (f.getType().isAssignableFrom(Map.class)) {
                    if (MapUtils.isEmpty((Map) f.get(this))) {
                        throw new MissingRequiredFieldException(String.format("field %s is empty", f.getName()));
                    }
                } else if (f.getType().isAssignableFrom(Collection.class)) {
                    if (CollectionUtils.isEmpty((Collection) f.get(this))) {
                        throw new MissingRequiredFieldException(String.format("field %s is empty", f.getName()));
                    }
                } else {
                    if (Objects.isNull(f.get(this))) {
                        throw new MissingRequiredFieldException(String.format("field %s is null", f.getName()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return asData();
    }
}
