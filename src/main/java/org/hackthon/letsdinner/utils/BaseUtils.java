package org.hackthon.letsdinner.utils;

import org.apache.commons.httpclient.HttpStatus;
import org.hackthon.letsdinner.model.AjaxObject;

public class BaseUtils
{
    public static String toResultJson(Object obj)
    {
        if (null == obj)
        {
            return AjaxObject.error(HttpStatus.SC_NOT_FOUND, "未找到记录").toString();
        }

        return AjaxObject.ok(obj).toString();
    }
}
