package org.hackthon.letsdinner.model;

/**
 * 选菜表
 */
public class MenuOne
{
    private String uid;

    private String menuIds;

    private String menuKey;

    private String currentDate;

    private String period;

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

    public String getCurrentDate()
    {
        return currentDate;
    }

    public void setCurrentDate(String currentDate)
    {
        this.currentDate = currentDate;
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
