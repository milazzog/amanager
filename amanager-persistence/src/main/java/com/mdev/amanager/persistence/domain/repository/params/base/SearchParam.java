package com.mdev.amanager.persistence.domain.repository.params.base;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gmilazzo on 05/10/2018.
 */
public class SearchParam {

    public SearchParam() {

        Class<?> c = this.getClass();

        List<Field> fields = Stream
                .of(c.getDeclaredFields())
                .collect(Collectors.toList());

        for (Field f : fields) {
            try {
                f.setAccessible(true);

                if (f.isAnnotationPresent(DefaultMatch.class) && f.getType().isAssignableFrom(StringMatcher.class)) {
                    DefaultMatch a = f.getAnnotation(DefaultMatch.class);
                    f.set(this, new StringMatcher(StringUtils.EMPTY, a.mode()));
                } else if (f.isAnnotationPresent(DatePattern.class) && f.getType().isAssignableFrom(DateMatcher.class)) {
                    DatePattern a = f.getAnnotation(DatePattern.class);
                    f.set(this, new DateMatcher(a.pattern()));
                } else {
                    Class<?> fieldClass = f.getType();
                    if(fieldClass.isAnnotationPresent(ValueMatcher.class)) {
                        f.set(this, fieldClass.newInstance());
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
