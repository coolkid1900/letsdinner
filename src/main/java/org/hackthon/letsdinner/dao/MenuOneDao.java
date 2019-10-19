package org.hackthon.letsdinner.dao;

import com.google.common.base.Joiner;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MenuOneDao
{
    private Logger logger = LoggerFactory.getLogger(MenuOneDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 存入选菜
     * @param date
     * @param period
     * @param menuIds
     * @return
     */
    public String addOneMenu(String uid, Map<Integer, Integer> menuIds, String key, String date, String period)
    {
        String sql = "insert into menu_one values(?,?,?,?,?)";
        try
        {
            String ids = Joiner.on("&").withKeyValueSeparator("=").join(menuIds);
            jdbcTemplate.update(sql, uid, ids, key, date, period);
        }
        catch (Exception exp)
        {
            logger.error("存入菜品失败. Exp: {}", exp.getMessage());
            return AjaxObject.error("存入菜品失败").toString();
        }
        return BaseUtils.toResultJson(new Object());
    }

}
