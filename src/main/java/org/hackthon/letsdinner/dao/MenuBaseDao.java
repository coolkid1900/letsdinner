package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuBaseDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 删除菜品，支持批量删除
     * @param menuIds
     * @return
     */
    public String deleteFromMenu(List<Integer> menuIds)
    {
        return BaseUtils.toResultJson(new Object());
    }

    /**
     * 增加单个菜品
     * @param name
     * @param price
     * @param image
     * @return
     */
    public String addDishToMenu(String name, BigDecimal price, String image)
    {
        return BaseUtils.toResultJson(new Object());
    }
}
