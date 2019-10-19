package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TakeProcessDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询取餐状态
     * @param key 取餐标识
     * @return
     */
    public String getDishStatus(String key)
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 设置取餐状态
     * @param key 取餐标识
     * @param status 取餐状态
     * @return
     */
    public String updateDishStatus(String key, String status)
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 查询当前取餐列表
     * @return
     */
    public String getAllMenu()
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 获取取餐码
     * @param key 取餐标识
     * @return
     */
    public String takeOneDish(String key)
    {
        return BaseUtils.toResultJson(new Object());
    }
}
