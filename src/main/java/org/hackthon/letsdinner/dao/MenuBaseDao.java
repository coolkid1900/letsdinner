package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class MenuBaseDao
{
    private Logger logger = LoggerFactory.getLogger(MenuBaseDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 删除菜品，支持批量删除
     * @param menuIds
     * @return
     */
    public String deleteFromMenu(final List<Integer> menuIds)
    {
        String sql = "delete from menu_base where id=?";
        try
        {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
            {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException
                {
                    preparedStatement.setInt(1, menuIds.get(i));
                }

                @Override
                public int getBatchSize()
                {
                    return menuIds.size();
                }
            });
        }
        catch (Exception exp)
        {
            logger.error("删除菜品失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("删除菜品失败").toString();
        }
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
        String sql = "insert into menu_base(name, image, price) values(?,?,?)";
        try
        {
            jdbcTemplate.update(sql, name, image, price);
        }
        catch (Exception exp)
        {
            logger.error("增加菜品失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("增加菜品失败").toString();
        }
        return BaseUtils.toResultJson(new Object());
    }
}
