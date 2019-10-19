package org.hackthon.letsdinner.dao;

import org.hackthon.letsdinner.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuOneDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 存入选菜
     * @param date
     * @param period
     * @param menuIds
     * @return
     */
    public String addOneMenu(String uid, List<Integer> menuIds, String key, String date, String period)
    {
        return BaseUtils.toResultJson(new Object());
    }

}
