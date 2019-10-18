package org.hackthon.letsdinner.controller;

import net.sf.json.JSONArray;
import org.hackthon.letsdinner.core.BusinessException;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.CurrentEater;
import org.hackthon.letsdinner.model.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.*;

@Controller
@RequestMapping("/show")
public class CurrentMenuShowController {
    @RequestMapping("/info")
    public String index(HttpServletRequest request, Model model)
    {
        return "CurrentEaterShow";
    }

    @RequestMapping("/queryCurrentUsers")
    public void queryCurrentUsers(HttpServletResponse resp){
        try
        {
            Food f1 = new Food("../images/ky.jpg","烤鸭");
            Food f2 = new Food("../images/sgcjd.jpg","丝瓜炒鸡蛋");
            ArrayList<Food> alFood = new ArrayList<Food>();
            alFood.add(f1);
            alFood.add(f2);
            //测试数据
            CurrentEater eateUser1 = new CurrentEater(1,alFood);
            CurrentEater eateUser2 = new CurrentEater(2,alFood);
            CurrentEater eateUser3 = new CurrentEater(3,alFood);
            CurrentEater eateUser4 = new CurrentEater(4,alFood);
            CurrentEater eateUser5 = new CurrentEater(5,alFood);
            CurrentEater eateUser6 = new CurrentEater(6,alFood);
            CurrentEater eateUser7 = new CurrentEater(7,alFood);
            CurrentEater eateUser8 = new CurrentEater(8,alFood);
            CurrentEater eateUser9 = new CurrentEater(9,alFood);
            CurrentEater eateUser10 = new CurrentEater(10,alFood);
            ArrayList<CurrentEater> currentEaters = new ArrayList<CurrentEater>();
            currentEaters.add(eateUser1);
            currentEaters.add(eateUser2);
            currentEaters.add(eateUser3);
            currentEaters.add(eateUser4);
            currentEaters.add(eateUser5);
            currentEaters.add(eateUser6);
            currentEaters.add(eateUser7);
            currentEaters.add(eateUser8);
            currentEaters.add(eateUser9);
            currentEaters.add(eateUser10);

            //将当前就餐者数据转换成json对象
            JSONArray data = JSONArray.fromObject(currentEaters);
            //设置编码，防止乱码
            resp.setCharacterEncoding("utf-8");
            //获得输出流
            PrintWriter respWritter = resp.getWriter();
            //发送Json格式对象字符串
            respWritter.append(data.toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
