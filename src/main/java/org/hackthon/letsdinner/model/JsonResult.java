package org.hackthon.letsdinner.model;

import com.alibaba.fastjson.JSON;

public class JsonResult
{
    private int code;

    private String message;

    private JSON data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public JSON getData()
    {
        return data;
    }

    public void setData(JSON data)
    {
        this.data = data;
    }
}
