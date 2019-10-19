package org.hackthon.letsdinner.utils;

import org.hackthon.letsdinner.model.PeriodBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 用餐工具类
public class PeriodUtil {
    // 获取当用餐时间
    public static String judgeCurrentTime (Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        try {
            Date nowTime = df.parse(df.format(date));
            Date breakfastStartTime = df.parse("07:00:00");
            Date breakfastEndTime = df.parse("09:00:00");
            if (isEffectiveDate(nowTime, breakfastStartTime, breakfastEndTime))
            {
                return PeriodBean.breakfast;
            }
            else
            {
                Date lunchStartTime = df.parse("11:00:00");
                Date lunchEndTime = df.parse("13:00:00");
                if (isEffectiveDate(nowTime, lunchStartTime, lunchEndTime))
                {
                    return PeriodBean.lunch;
                }
                else {
                    Date dinnerStartTime = df.parse("17:00:00");
                    Date dinnerEndTime = df.parse("19:00:00");
                    if (isEffectiveDate(nowTime, dinnerStartTime, dinnerEndTime))
                    {
                        return PeriodBean.dinner;
                    }
                    else {
                        // 默认取晚餐菜表
                        return PeriodBean.dinner;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("转换日期失败：\n" + e.getMessage() + " " + e.getCause());
            return null;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return true/false
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
