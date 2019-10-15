package zxx.pri.core.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @program: yunce-cloud
 * @description:
 * @author: TangChao
 * @create: 2019-01-18 15:35
 **/

public class DateUtils {
    /**
     * 获取当前系统时间
     **/
    public static String getCurrentTime(SimpleDateFormat simpleDateFormat) {
        Date day = new Date();
        String currentTime = simpleDateFormat.format(day);
        return currentTime;
    }

    /**
     * 获取当前系统前一天的时间
     *
     * @Author TangChao
     **/
    public static String getYesterdayTime(SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取系统一周前的时间
     *
     * @Author TangChao
     **/
    public static String getlastWeakTime(SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取系统一月前的时间
     **/
    public static String getlastMonthTime(SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取系统三月前的时间
     **/
    public static String getlastThreeTime(SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前系统时间前一年的时间
     **/
    public static String getLastYear(SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }


}
