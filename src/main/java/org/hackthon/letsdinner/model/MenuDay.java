package org.hackthon.letsdinner.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 每日菜表
 */
public class MenuDay
{
    @JSONField(serialize = false)
    private String menuIds;

    private String currentDate;

    private String period;

    private List<String> menuIdList;

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

    public List<String> getMenuIdList()
    {

        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList)
    {
        this.menuIdList = menuIdList;
    }
}
