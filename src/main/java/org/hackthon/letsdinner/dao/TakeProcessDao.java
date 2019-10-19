package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.Dish;
import org.hackthon.letsdinner.model.MenuBase;
import org.hackthon.letsdinner.model.MenuOne;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TakeProcessDao
{
    private Logger logger = LoggerFactory.getLogger(TakeProcessDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询取餐状态
     * @param key 取餐标识
     * @return
     */
    public String getDishStatus(String key)
    {
        String sql = "select status from take_process where menu_key=?";
        Map<String, String> map = new HashMap<>();
        try
        {
            String status = jdbcTemplate.queryForObject(sql, new Object[]{key}, String.class);
            map.put("status", status);
        }
        catch (EmptyResultDataAccessException exp)
        {
            // 查询列表为空
            map.put("status", "D");
        }
        catch (Exception exp)
        {
            logger.error("查询取餐状态失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("查询取餐状态失败").toString();
        }
        return BaseUtils.toResultJson(map);
    }

    /**
     * 设置取餐状态
     * @param key 取餐标识
     * @param status 取餐标识
     * @return
     */
    public String updateDishStatus(String key, String status)
    {
        String sql = "update take_process set status=? where menu_key=?";
        try
        {
            jdbcTemplate.update(sql, status, key);
        }
        catch (Exception exp)
        {
            logger.error("设置取餐状态失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("设置取餐状态失败").toString();
        }
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 查询当前取餐列表
     * @return
     */
    public String getAllMenu()
    {
        String sql1 = "select id,uid,menu_ids,a.menu_key as menu_key,today_date,period from take_process a,menu_one b " +
                "where a.menu_key = b.menu_key and status='I'";
        String sql2 = "select id,name,image,price from menu_base where id=?";

        List<Map<String, Object>> list = new ArrayList<>();
        try
        {
            List<MenuOne> menuOnes = jdbcTemplate.query(sql1, new Object[]{}, new BeanPropertyRowMapper<>(MenuOne.class));
            for (MenuOne one : menuOnes)
            {
                Map<String, Object> map = new HashMap<>();
                List<Dish> dishes = new ArrayList<>();
                map.put("id", one.getId());
                map.put("menu", dishes);
                String[] menuIds = one.getMenuIds().split("&"); // 1=2&2=1
                for (String menuId : menuIds)
                {
                    String sid = menuId.substring(0, menuId.indexOf('='));
                    String scnt = menuId.substring(menuId.indexOf('=') + 1);
                    MenuBase base = jdbcTemplate.queryForObject(sql2, new Object[]{Integer.parseInt(sid)}, new BeanPropertyRowMapper<>(MenuBase.class));
                    Dish dish = new Dish();
                    dish.setName(base.getName());
                    dish.setImage(base.getImage());
                    dish.setCount(Integer.parseInt(scnt));
                    dishes.add(dish);
                }
                list.add(map);
            }
        }
        catch (Exception exp)
        {
            logger.error("查询当前取取餐列表失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("查询当前取取餐列表失败").toString();
        }
        if (list.size() == 0)
        {
            return AjaxObject.error(404, "查询记录为空").toString();
        }
        return BaseUtils.toResultJson(list);
    }

    /**
     * 获取取餐码
     * @param key 取餐标识
     * @return
     */
    public String takeOneDish(String key)
    {
        String sql1 = "select id from take_process where status<>'I' order by id limit 1";
        String sql2 = "update take_process set status='I' where id=?";
        Map<String, Integer> map = new HashMap<>();
        try
        {
            int id = jdbcTemplate.queryForObject(sql1, new Object[]{}, Integer.class);
            jdbcTemplate.update(sql2, id);
            map.put("id", id);
        }
        catch (Exception exp)
        {
            logger.error("获取取餐码失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("获取取餐码失败").toString();
        }
        if (map.size() == 0)
        {
            return AjaxObject.error(404, "查询记录为空").toString();
        }
        return BaseUtils.toResultJson(map);
    }

    public String takeOneDishOrSetStatus(String key, String status)
    {
        String sql1 = "select id from take_process where menu_key=?";
        String sql2 = "select id from take_process where status<>'I' order by id limit 1";
        String sql3 = "update take_process set status=? where menu_key=?";
        String sql4 = "update take_process set status=?,menu_key=? where id=?";

        Map<String, Object> map = new HashMap<>();
        try
        {
            int id = jdbcTemplate.queryForObject(sql1, new Object[]{key}, Integer.class);
            // 有记录，则设置状态
            jdbcTemplate.update(sql3, status, key);
            map.put("id", id);
        }
        catch (EmptyResultDataAccessException exp)
        {
            // 查询列表为空，写入数据库
            try
            {
                int id = jdbcTemplate.queryForObject(sql2, new Object[]{}, Integer.class);
                jdbcTemplate.update(sql4, status, key, id);
                map.put("id", id);
            }
            catch (EmptyResultDataAccessException e)
            {
                return AjaxObject.error(404, "当前无空闲取餐码").toString();
            }
            catch (Exception e)
            {
                logger.error("获取取餐码失败#1. Exp: {}", e.getMessage());
                return AjaxObject.error("查询取餐状态失败").toString();
            }
        }
        catch (Exception exp)
        {
            logger.error("获取取餐码失败#2. Exp: {}", exp.getMessage());
            return AjaxObject.error("获取取餐码失败").toString();
        }
        return BaseUtils.toResultJson(map);
    }
}
