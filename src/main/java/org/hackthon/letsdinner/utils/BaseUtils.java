package org.hackthon.letsdinner.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpStatus;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.JsonResult;

import java.util.Random;

public class BaseUtils
{
    private final static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'x',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String toResultJson(Object obj)
    {
        if (null == obj)
        {
            return AjaxObject.error(HttpStatus.SC_NOT_FOUND, "未找到记录").toString();
        }

        return AjaxObject.ok(obj).toString();
    }

    public static JsonResult parseJson(String json)
    {
        return JSON.parseObject(json, JsonResult.class);
    }

    public static String generatorString(int length)
    {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            builder.append(chars[random.nextInt(chars.length)]);
        }
        return builder.toString();
    }

}
