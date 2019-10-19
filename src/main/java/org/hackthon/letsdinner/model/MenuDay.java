package org.hackthon.letsdinner.model;

/**
 * 每日菜表
 */
public class MenuDay
{
    private String menuIds;

    private String todayDate;

    private String period;

    public String getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(String menuIds)
    {
        this.menuIds = menuIds;
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
