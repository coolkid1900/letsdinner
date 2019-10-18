package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSONObject;
import org.hackthon.letsdinner.model.food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        String userID = "";
        if (request.getParameterMap().get("userID") != null) {
            userID = request.getParameterMap().get("userID")[0];
        }

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        String date = df.format(new Date());  // new Date()为获取当前系统时间，也可使用当前时间戳

        // 根据日期获取菜品信息

        //菜品信息初始化
        ArrayList menu = new ArrayList();
        for(int i = 0; i < 12; i++) {
            food f = new food();
            f.foodId = i;
            f.foodName = "宫保鸡丁" + i;
            f.price = 8;
            menu.add(f);
        }
        model.addAttribute("menu", menu);
        model.addAttribute("userID", userID);
        return "userMenu";
    }

    @RequestMapping(value = "/userCommit", method = RequestMethod.POST)
    public
    @ResponseBody
    String userCommit(HttpServletRequest request)
    {
        // 获取用户ID及选择的菜品
        String sendStr = request.getParameterMap().get("sendJson")[0];
        JSONObject sendJson = JSONObject.parseObject(sendStr);
        String userID = sendJson.get("userID").toString();
        String foodStr = sendJson.get("foodJson").toString();
        foodStr = foodStr.replace("[", "{").replace("]", "}");

        // 加入时间戳，生成唯一ID
        String uniqueID = System.currentTimeMillis() + userID;

        //将用户选择的菜单存入数据库

        JSONObject returnJson = new JSONObject();
        returnJson.put("uniqueID", uniqueID);

        return returnJson.toString();
    }

    /*@RequestMapping("/menu")
    public ModelAndView  itemsList() {
        String sql = "select * from items";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        ModelAndView mav = new ModelAndView("items");
        mav.addObject("list", list);
        return mav;
    }*/
}
