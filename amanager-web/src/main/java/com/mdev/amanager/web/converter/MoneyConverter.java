package com.mdev.amanager.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@FacesConverter("moneyConverter")
public class MoneyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                value = value.replace(",", ".");
                BigDecimal big = new BigDecimal(value);
                big = fixAmount(big);
                return big;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof BigDecimal) {
            BigDecimal amount = (BigDecimal) value;
            amount = fixAmount(amount);
            return String.valueOf(amount.toPlainString());
        } else {
            return null;
        }
    }

    private BigDecimal fixAmount(BigDecimal amount) {
        if (amount.scale() < 2)
            amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        else if (amount.scale() > 3)
            amount = amount.setScale(3, BigDecimal.ROUND_HALF_UP);
        return amount;
    }
}
