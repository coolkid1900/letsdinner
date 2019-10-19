package org.hackthon.letsdinner.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpStatus;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.JsonResult;

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

    public static JsonResult parseJson(String json)
    {
        return JSON.parseObject(json, JsonResult.class);
    }

}
