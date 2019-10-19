package org.hackthon.letsdinner.dao;

import com.google.common.base.Joiner;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.MenuBase;
import org.hackthon.letsdinner.model.MenuDay;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.hackthon.letsdinner.utils.DataChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuDayDao
{
    private Logger logger = LoggerFactory.getLogger(MenuDayDao.class);
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
        List<MenuBase> list = new ArrayList<>();
        String sql1 = "select menu_ids,current_date,period from menu_day where current_date=? and period=?";
        String sql2 = "select id,name,image,price from menu_base where id=?";
        try
        {
            List<MenuDay> menuDays = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<>(MenuDay.class), date, period);
            MenuDay menuDay = DataAccessUtils.uniqueResult(menuDays);
            DataChecker.checkNull(menuDay);
            String[] menuIds = menuDay.getMenuIds().split(",");
            for (String id : menuIds)
            {
                MenuBase base = jdbcTemplate.queryForObject(sql2, new Object[]{Integer.parseInt(id)}, new BeanPropertyRowMapper<>(MenuBase.class));
                DataChecker.checkNull(base);
                list.add(base);
            }
        }
        catch (Exception exp)
        {
            logger.error("查询每日菜单表失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("查询每日菜单表失败").toString();
        }
        if (list.size() == 0)
        {
            return AjaxObject.error(404, "查询记录为空").toString();
        }
        return BaseUtils.toResultJson(list);
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
        String sql = "insert into menu_day(menu_ids, current_date, period) values(?,?,?)";
        try
        {
            String ids = Joiner.on(',').join(menuIds);
            jdbcTemplate.update(sql, ids, date, period);
        }
        catch (Exception exp)
        {
            logger.error("添加每日菜品失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("添加每日菜品失败").toString();
        }
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
        String sql = "update menu_day set menu_ids=? where date=? and period=?";
        try
        {
            String ids = Joiner.on(',').join(menuIds);
            jdbcTemplate.update(sql, ids, date, period);
        }
        catch (Exception exp)
        {
            logger.error("更新菜品失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("更新菜品失败").toString();
        }
        return BaseUtils.toResultJson(new Object());
    }
}
