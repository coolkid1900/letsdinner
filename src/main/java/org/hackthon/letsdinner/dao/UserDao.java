package org.hackthon.letsdinner.dao;

import com.google.common.hash.Hashing;
import org.hackthon.letsdinner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(String id, String name, String password)
    {
        password = Hashing.md5().hashBytes(password.getBytes()).toString();
        jdbcTemplate.update("insert into user values(?,?,?);", id, name, password);
    }

    public boolean validateUser(String id, String password)
    {
        password = Hashing.md5().hashBytes(password.getBytes()).toString();
        System.out.println(password);
        String sql = "select * from user where id=?";

        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        System.out.println(user.getName());
        System.out.println(user.getPassword().equals(password));
        return true;
    }
}
