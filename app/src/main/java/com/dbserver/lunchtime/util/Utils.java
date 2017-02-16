package com.dbserver.lunchtime.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class implements useful functionalities used along the application
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/13/2017
 */
public class Utils {

    public static Date convertStringToDate(String data) {
        try {
            SimpleDateFormat simpleDateFormat = null;
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Date date = simpleDateFormat.parse(data);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
