package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hackthon.letsdinner.dao.MenuDayDao;
import org.hackthon.letsdinner.dao.MenuOneDao;
import org.hackthon.letsdinner.model.FoodBean;
import org.hackthon.letsdinner.model.SelectBean;
import org.hackthon.letsdinner.utils.PeriodUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
public class UserMenuController {
    /**
     * 用户菜单页面
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/userMenu")
    public String userMenu(HttpServletRequest request, Model model)
    {
        // 获取用户ID
        String userID = "";
        if (request.getParameterMap().get("userID") != null) {
            userID = request.getParameterMap().get("userID")[0];
        }
        //菜品信息初始化
        ArrayList menu = new ArrayList();

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  //设置日期格式
        Date currentDt = new Date();
        String date = df.format(currentDt);
        // 获取当前用餐时间段
        String mealTime = PeriodUtil.judgeCurrentTime(currentDt);
        // 获取时间段失败
        if (mealTime == null)
        {
            return "error";
        }
        // 根据日期和用餐类型获取菜品信息
        MenuDayDao mdd = new MenuDayDao();
        String foodListStr = mdd.getDayMenu(date, mealTime);

        /*// 调试代码
        String foodListStr = "";
        String encoding = "UTF-8";
        File file = new File("D:\\fsq\\test.txt");
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            foodListStr = new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }*/

        // json解析
        JSONObject foodListJson = JSONObject.parseObject(foodListStr);
        Integer statusCode = foodListJson.getInteger("code");
        // 获取菜单列表成功
        if (statusCode == 0)
        {
            JSONArray foodList = foodListJson.getJSONArray("data");
            for(int i = 0; i < foodList.size(); i++) {
                JSONObject foodJson = (JSONObject) foodList.get(i);
                FoodBean everyFood = JSON.toJavaObject(foodJson, FoodBean.class);
                menu.add(everyFood);
            }
        }
        else {
            return "errorNew";
        }

        model.addAttribute("menu", menu);
        model.addAttribute("userID", userID);
        model.addAttribute("date", date);
        model.addAttribute("period", mealTime);
        return "userMenu";
    }

    @RequestMapping(value = "/userCommit", method = RequestMethod.POST)
    public
    @ResponseBody
    String userCommit(HttpServletRequest request)
    {
        // 获取用户ID及选择的菜品
        String sendStr = request.getParameterMap().get("foodJson")[0];
        String date = request.getParameterMap().get("date")[0];
        String period = request.getParameterMap().get("period")[0];

        JSONObject sendJson = JSONObject.parseObject(sendStr);
        String userID = sendJson.get("userID").toString();
        String foodStr = sendJson.get("foodJson").toString();

        // 存储菜品ID和数量
        HashMap<Integer, Integer> foodMap = new HashMap<Integer, Integer>();
        JSONArray foodArray = JSONArray.parseArray(foodStr);
        for(int i = 0; i < foodArray.size(); i++) {
            JSONObject foodJson = (JSONObject) foodArray.get(i);
            SelectBean everyFood = JSON.toJavaObject(foodJson, SelectBean.class);
            foodMap.put(everyFood.id, everyFood.num);
        }
        // 加入时间戳，生成唯一ID
        String uniqueID = System.currentTimeMillis() + userID;

        //将用户选择的菜单存入数据库
        MenuOneDao mod = new MenuOneDao();
        String saveMsg = mod.addOneMenu(userID, foodMap, uniqueID, date, period);
        /*String saveMsg = "{\n" +
                "    \"code\": 0,\n" +
                "    \"message\":\"\",\n" +
                "    \"data\": {\n" +
                "    }\n" +
                "  }";*/
        JSONObject foodListJson = JSONObject.parseObject(saveMsg);
        Integer statusCode = foodListJson.getInteger("code");
        JSONObject returnJson = new JSONObject();
        // 存储用户选菜列表成功
        if (statusCode == 0)
        {
            returnJson.put("code", 0);
            returnJson.put("uniqueID", uniqueID);
        }
        else {
            returnJson.put("code", -1);
        }
        return returnJson.toString();
    }
}
