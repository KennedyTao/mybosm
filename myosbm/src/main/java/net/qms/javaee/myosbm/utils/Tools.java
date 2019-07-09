package net.qms.javaee.myosbm.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    /**
     * change date to timestamp.
     */
    public static Timestamp toTimeStamp(Date date){

        Timestamp result = null;

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        result = Timestamp.valueOf(time);

        return result;
    }
}
