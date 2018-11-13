package com.mdev.amanager.web.converter;

import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.model.RawProduct;
import com.mdev.amanager.persistence.domain.repository.RawProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Objects;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@FacesConverter("rawProductConverter")
public class RawProductConverter extends SpringBeanAutowiringSupport implements Converter {

    @Autowired
    private RawProductRepository rawProductRepository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Objects.nonNull(value)) {
            try {
                return rawProductRepository.find(Long.parseLong(value));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (Objects.nonNull(value) && value instanceof RawProduct) {

            return String.valueOf(((RawProduct) value).getId());
        }
        return null;
    }
}
