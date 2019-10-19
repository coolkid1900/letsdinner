package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.cookBean;
import org.hackthon.letsdinner.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

@Controller
@RequestMapping("/letsdinner")
public class RestaurantController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public RestaurantController(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/restaurant")
    public String Restaurant(HttpServletRequest request, Model model)
    {
        return "restaurant2";
        //return "nav";
    }


    /*@RequestMapping("/show")
    public String showImgList(String imgPath, Model model)
    {
        try {
            //测试直接将图片地址给前端展示
            model.addAttribute("imgPath", "C:/letsdinner/letsdinner/target/classes/static/images/upLoaded/1571229707528bcs.jpg");
            return "show";
        }
        catch(Exception e)
        {
            return "error";
        }
    }*/

    /**
     * ajax请求json成功示例，需要添加两个注解@ResponseBody和@RequestMapping
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的json对象
     * json格式如下：
     * {
     *     code：0，
     *     msg：“获取列表成功”，
     *     data：{
     *          "user": "linxu"
     *     }
     * }
     */
    @ResponseBody
    @RequestMapping("/upload")
    public AjaxObject upload(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model)
    {
        try{
            Map<String, Object> map = new HashMap<>();
            //菜名
            String strCookName = request.getParameter("cookName");
            //单价
            String strCookPrice = request.getParameter("cookPrice");
            //图片转换为base64
            InputStream ins = file.getInputStream();

            String base64Img = ImageUtil.image2Base64byinputStream(ins);
            //导入菜谱表
            //...todo

            return AjaxObject.ok("上传成功");
        }
        catch(Exception e)
        {
            return AjaxObject.error("上传失败");
        }
    }

    @ResponseBody
    @RequestMapping("/upload2")
    public AjaxObject upload2(@RequestBody String json, Model model)
    {
        try{
            JSONObject jsonObject = JSON.parseObject(json);
            //获取数据
            String strCookName = jsonObject.getString("cookName");
            String strCookPrice = jsonObject.getString("cookPrice");
            JSONObject fileObject = jsonObject.getJSONObject("file");
            MultipartFile file = (MultipartFile)fileObject;
            //图片转换为base64
            InputStream ins = file.getInputStream();

            String base64Img = ImageUtil.image2Base64byinputStream(ins);
            //导入菜谱表
            //...todo

            return AjaxObject.ok("上传成功");
        }
        catch(Exception e)
        {
            return AjaxObject.error("上传失败");
        }
    }

    /**
     *
     * @param json
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertDayCook")
    public AjaxObject ajax(@RequestBody String json, Model model)
    {
        try
        {
            JSONObject jsonObject = JSON.parseObject(json);
            //JSONObject jsonObject1 = JSONObject.parseObject(COMPLEX_JSON_STR);//因为JSONObject继承了JSON，所以这样也是可以的

            //获取日期，用餐时间段，菜谱id
            String strWeek = jsonObject.getString("week");
            String strTimePeriod = jsonObject.getString("timePeriod");
            JSONArray idList = jsonObject.getJSONArray("list");
            //调用接口插入每日菜谱


            return AjaxObject.ok("插入成功！");
        }
        catch(Exception e)
        {
            return AjaxObject.error("插入失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/showcookbook")
    public Map<String, Object> getCookBook()
    {
        Map<String, Object> map=new HashMap<>();
        cookBean cook1=new cookBean();
        cook1.id="1";
        cook1.name="红烧肉";
        cook1.price="12.00";
        String testFileName="C:/letsdinner/src/main/resources/static/images/cookery-book/bcs.jpg";

        String result= ImageUtil.image2Base64(testFileName);
        cook1.image=result;
        cookBean cook2=new cookBean();
        cook2.id="2";
        cook2.name="红烧肉2";
        cook2.price="13.00";
        cook2.image=result;
        List<cookBean> cookList = new ArrayList<cookBean>();
        cookList.add(cook1);
        cookList.add(cook2);

        //获取当前完整菜谱
        //todo...


        map.put("total", 2);
        map.put("rows", cookList);
        return map;
    }

    @ResponseBody
    @RequestMapping("/showDayCookBook")
    public Map<String, Object> getDayCookBook(@RequestBody String json, Model model)
    {
        Map<String, Object> map1 = new HashMap<>();
        try
        {
            JSONObject jsonObject = JSON.parseObject(json);
            //获取日期，用餐时间段
            String strWeek = jsonObject.getString("week");
            String strTimePeriod = jsonObject.getString("time");

            cookBean cook1=new cookBean();
            cook1.id="1";
            cook1.name="红烧肉";
            cook1.price="12.00";
            String testFileName="C:/letsdinner/src/main/resources/static/images/cookery-book/bcs.jpg";

            String result= ImageUtil.image2Base64(testFileName);
            cook1.image=result;
            cookBean cook2=new cookBean();
            cook2.id="2";
            cook2.name="红烧肉2";
            cook2.price="13.00";
            cook2.image=result;
            List<cookBean> cookList = new ArrayList<cookBean>();
            cookList.add(cook1);
            cookList.add(cook2);

            //获取当前完整菜谱
            //todo...


            map1.put("total", 2);
            map1.put("rows", cookList);
            return map1;
        }
        catch(Exception e)
        {
            return map1;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteCookBook")
    public AjaxObject deletCookBook(HttpServletRequest request, Model model)
    {
        try{
            String id = request.getParameter("id");
            //删除当前菜品
            //todo...
            return AjaxObject.ok("删除成功！");
        }
        catch(Exception e)
        {
            return AjaxObject.error("删除失败！");
        }
    }

}

