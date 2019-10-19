package org.hackthon.letsdinner.utils;

import org.hackthon.letsdinner.core.BusinessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataChecker
{
    public static void checkDate(String date)
    {
        try
        {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        catch (Exception exp)
        {
            throw new BusinessException("无效的日期格式，必须为：yyyy-MM-dd");
        }
    }

    public static void checkPeriod(String period)
    {
        if (!"B".equals(period) && !"L".equals(period) && !"D".equals(period))
        {
            throw new BusinessException("无效的用餐时间段");
        }
    }

    public static void checkNull(Object obj) throws Exception
    {
        if (null == obj)
        {
            throw new Exception("object is null");
        }
    }
}
