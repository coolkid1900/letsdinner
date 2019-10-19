package org.hackthon.letsdinner.model;

/**
 * 每日菜表
 */
public class MenuDay
{
    private String menuIds;

    private String currentDate;

    private String period;

    public String getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(String menuIds)
    {
        this.menuIds = menuIds;
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
