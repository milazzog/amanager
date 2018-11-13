package com.mdev.amanager.core.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gmilazzo on 16/10/2018.
 */
public class CommonUtil {

    private static final String DD = "dd";

    public static Date roundToNearestMonth() {

        return roundToNearestMonth(new Date());
    }

    public static Date roundToNearestMonth(Date date) {

        int dd = Integer.valueOf((new SimpleDateFormat(DD)).format(date));
        int limit = BigDecimal.valueOf(Integer.valueOf((new SimpleDateFormat(DD)).format(DateUtils.addDays(DateUtils.addMonths(DateUtils.truncate(date, Calendar.MONTH), 1), -1)))).divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_UP).intValue();

        if (dd >= limit) {
            return DateUtils.addMonths(DateUtils.truncate(date, Calendar.MONTH), 1);
        } else {
            return DateUtils.truncate(date, Calendar.MONTH);
        }
    }

    public static BigDecimal money(BigDecimal decimal) {

        BigDecimal res = ObjectUtils.defaultIfNull(decimal, BigDecimal.ZERO);

        return res.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String moneyStr(BigDecimal decimal) {

        BigDecimal res = ObjectUtils.defaultIfNull(decimal, BigDecimal.ZERO);

        return res.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static int toInt(BigDecimal decimal) {

        BigDecimal res = ObjectUtils.defaultIfNull(decimal, BigDecimal.ZERO);

        return res.setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static String dateStr(Date date) {
        return (new SimpleDateFormat("dd-MM-yyyy")).format(date);
    }

    public static String timestampStr(Date timestamp) {
        return (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(timestamp);
    }

    public static String reduce(String in, String truncation, int maxLen) {

        int len1 = StringUtils.length(in);
        int len2 = StringUtils.length(truncation);

        if (len1 <= maxLen) {
            return in;
        } else {
            return in.substring(0, maxLen - len2) + truncation;
        }
    }
}
