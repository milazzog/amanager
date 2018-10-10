package org.mdev.amanager.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gmilazzo on 05/10/2018.
 */
public class TestUtil {

    public static Date date(String date){
        try {
            return (new SimpleDateFormat("dd-MM-yyyy")).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date timestamp(String timestamp){
        try {
            return (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).parse(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }
}
