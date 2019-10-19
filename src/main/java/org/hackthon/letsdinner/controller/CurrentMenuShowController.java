package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.hackthon.letsdinner.dao.TakeProcessDao;
import org.hackthon.letsdinner.model.CurrentEater;
import org.hackthon.letsdinner.model.FoodBean;
import org.hackthon.letsdinner.model.JsonResult;
import org.hackthon.letsdinner.model.RestaurantFood;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/show")
public class CurrentMenuShowController {
    @Autowired
    private TakeProcessDao TakeProcessDao;

    @RequestMapping("/info")
    public String index(HttpServletRequest request, Model model)
    {
        return "CurrentEaterShow";
    }

    @RequestMapping("/queryCurrentUsers")
    public void queryCurrentUsers(HttpServletResponse resp){
        try
        {
            //接口调用，查询当前取餐列表，getAllMenu
            String menuInfo = TakeProcessDao.getAllMenu();
            JsonResult jsonResult = BaseUtils.parseJson(menuInfo);
            JSON json = jsonResult.getData();
            //测试代码
            ArrayList<CurrentEater> currentEaterTest = (ArrayList)JSON.parseArray(json.toJSONString(),CurrentEater.class);
            //JavaBean对象转字符串
            String data = JSONArray.toJSON(currentEaterTest).toString();

            //设置编码，防止乱码
            resp.setCharacterEncoding("utf-8");
            //获得输出流
            PrintWriter respWritter = resp.getWriter();
            //发送Json格式对象字符串
            respWritter.append(data);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
