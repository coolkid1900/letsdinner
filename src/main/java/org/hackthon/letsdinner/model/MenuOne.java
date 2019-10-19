package org.hackthon.letsdinner.model;

import java.sql.Date;

/**
 * 选菜表
 */
public class MenuOne
{
    private int id;

    private String uid;

    private String menuIds;

    private String menuKey;

    private String todayDate;

    private String period;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(String menuIds)
    {
        this.menuIds = menuIds;
    }

    public String getMenuKey()
    {
        return menuKey;
    }

    public void setMenuKey(String menuKey)
    {
        this.menuKey = menuKey;
    }

    public String getTodayDate()
    {
        return todayDate;
    }

    public void setTodayDate(String todayDate)
    {
        this.todayDate = todayDate;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }
}
