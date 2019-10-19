package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuDayDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询每日菜单表
     * @param date 日期
     * @param period 用餐时间段
     * @return
     */
    public String getDayMenu(String date, String period)
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 添加每日菜单
     * @param date 日期
     * @param period 用餐时间段
     * @param menuIds 菜品ID列表
     * @return
     */
    public String addDayMenu(String date, String period, List<Integer> menuIds)
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 更新每日菜单
     * @param date 日期
     * @param period 用餐时间段
     * @param menuIds 菜品ID列表
     * @return
     */
    public String updateDayMenu(String date, String period, List<Integer> menuIds)
    {
        return BaseUtils.toResultJson(new Object());
    }
}
