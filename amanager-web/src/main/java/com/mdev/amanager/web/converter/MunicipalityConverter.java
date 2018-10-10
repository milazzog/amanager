package com.mdev.amanager.web.converter;

import com.mdev.amanager.persistence.domain.model.Municipality;
import com.mdev.amanager.persistence.domain.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Objects;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@FacesConverter("municipalityConverter")
public class MunicipalityConverter extends SpringBeanAutowiringSupport implements Converter {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(Objects.nonNull(value)) {
            try {
                return municipalityRepository.find(Long.parseLong(value));
            }catch (NumberFormatException e){
                return null;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(Objects.nonNull(value) && value instanceof Municipality){

            return String.valueOf(((Municipality) value).getId());
        }
        return null;
    }
}
