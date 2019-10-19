package org.hackthon.letsdinner.utils;

import org.hackthon.letsdinner.core.BusinessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataChecker
{
    public static void CheckDate(String date)
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

    public static void CheckPeriod(String period)
    {
        if (!"B".equals(period) && !"L".equals(period) && !"D".equals(period))
        {
            throw new BusinessException("无效的用餐时间段");
        }
    }
}
