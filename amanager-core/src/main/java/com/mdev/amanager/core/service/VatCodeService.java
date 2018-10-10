package com.mdev.amanager.core.service;

import com.mdev.amanager.persistence.domain.enums.Gender;
import com.mdev.amanager.persistence.domain.model.Municipality;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Service
public class VatCodeService {

    private static final String SPACES = "\\h+";
    private static final String NOT_VOCAL = "[^AEIOU]";
    private static final String VOCAL = "[AEIOU]";

    private String[] control;
    private String[] months;
    private int[] spare;
    private int[] pare;

    @PostConstruct
    public void init() {

        control = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        months = new String[]{"A", "B", "C", "D", "E", "H", "L", "M", "P", "R", "S", "T"};
        spare = new int[]{1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        pare = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    }

    public String getVatCode(String lastName, String firstName, Date birthDate, Gender gender, Municipality birthCity) {

        String partial = extractLastName(lastName) + extractFirstName(firstName) + extractDate(birthDate, gender) + birthCity.getBelfioreCode();
        char[] chars = partial.toCharArray();
        int sum = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                chars[i] = (char) ((int) chars[i] + 17);
            }
            sum += ((i + 1) % 2 == 0) ? pare[(int) chars[i] - 65] : spare[(int) chars[i] - 65];
        }
        return partial + control[sum % 26];
    }

    private String extractLastName(String lastName) {
        String vocal = lastName.replaceAll(SPACES, StringUtils.EMPTY).toUpperCase().replaceAll(NOT_VOCAL, StringUtils.EMPTY);
        String consonants = lastName.replaceAll(SPACES, StringUtils.EMPTY).toUpperCase().replaceAll(VOCAL, StringUtils.EMPTY);
        return merge(consonants, vocal);
    }

    private String extractFirstName(String firstName) {
        String vocal = firstName.replaceAll(SPACES, StringUtils.EMPTY).toUpperCase().replaceAll(NOT_VOCAL, StringUtils.EMPTY);
        String consonants = firstName.replaceAll(SPACES, StringUtils.EMPTY).toUpperCase().replaceAll(VOCAL, StringUtils.EMPTY);
        if (consonants.length() >= 4) {
            return consonants.substring(0, 1) + consonants.substring(2, 4);
        } else {
            return merge(consonants, vocal);
        }
    }

    private String extractDate(Date date, Gender gender) {
        String[] raw = (new SimpleDateFormat("dd-MM-yyyy")).format(date).split("-");
        String dd = gender == Gender.M ? StringUtils.leftPad(raw[0], 2, '0') : String.valueOf(Integer.parseInt(raw[0]) + 40);
        String mm = months[Integer.parseInt(raw[1]) - 1];
        String yy = StringUtils.leftPad(String.valueOf(Integer.parseInt(raw[2]) % 100), 2, '0');
        return yy + mm + dd;
    }

    private String merge(String consonants, String vocal) {
        if (consonants.length() >= 3) {
            return consonants.substring(0, 3);
        } else {
            int needed = 3 - consonants.length();
            if (vocal.length() >= needed) {
                return consonants + vocal.substring(0, needed);
            } else {
                return StringUtils.rightPad(consonants + vocal, 3, 'X');
            }
        }
    }
}

