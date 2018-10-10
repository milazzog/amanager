package com.mdev.amanager.persistence.domain.repository.params.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatePattern {

    String pattern() default DateMatcher.DEFAULT_PATTERN;
}
