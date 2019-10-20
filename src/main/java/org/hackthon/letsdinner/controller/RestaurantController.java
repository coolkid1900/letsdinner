package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hackthon.letsdinner.dao.MenuBaseDao;
import org.hackthon.letsdinner.dao.MenuDayDao;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.model.JsonResult;
import org.hackthon.letsdinner.model.MenuBase;
import org.hackthon.letsdinner.model.cookBean;
import org.hackthon.letsdinner.utils.BaseUtils;
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
import java.math.BigDecimal;
import java.nio.file.FileSystemNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/letsdinner")
public class RestaurantController {

    @Autowired
    private MenuBaseDao menuBaseDao;
    @Autowired
    private MenuDayDao menuDayDao;

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
            menuBaseDao.addDishToMenu(strCookName, new BigDecimal(strCookPrice), base64Img);
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
            int weekDay = Integer.parseInt(strWeek);

            LocalDate today = LocalDate.now();
            int diff = weekDay - today.getDayOfWeek().getValue();

            List<Integer> idList = jsonObject.getJSONArray("list").toJavaList(Integer.class);
            //调用接口插入每日菜谱
            menuDayDao.addDayMenu(today.plusDays(diff).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), strTimePeriod, idList);

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
//        JSONObject jsonObject = JSON.parseObject(menuBaseDao.getAllMenu());
        JsonResult result = BaseUtils.parseJson(menuBaseDao.getAllMenu());
        JSONArray array = (JSONArray) result.getData();
        map.put("total", array.size());
        map.put("rows", array);
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

            int weekDay = Integer.parseInt(strWeek);

            LocalDate today = LocalDate.now();
            int diff = weekDay - today.getDayOfWeek().getValue();

            String strTimePeriod = jsonObject.getString("time");

            //获取当前完整菜谱
            //todo...
            JsonResult result = BaseUtils.parseJson(menuDayDao.getDayMenu(today.plusDays(diff).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), strTimePeriod));
            JSONArray array = (JSONArray) result.getData();

            map1.put("total", array.size());
            map1.put("rows", array);
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
            int i_id = Integer.parseInt(id);
            ArrayList idArray = new ArrayList();
            idArray.add(i_id);
            List<Integer> idList = (List<Integer>)idArray;
            //调用接口插入每日菜谱
            menuBaseDao.deleteFromMenu(idList);
            return AjaxObject.ok("删除成功！");
        }
        catch(Exception e)
        {
            return AjaxObject.error("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/deleteDayCookBook")
    public AjaxObject deletDayCookBook(HttpServletRequest request, Model model)
    {
        try{
            String id = request.getParameter("id");
            //删除当前菜品
            //todo...
            int i_id = Integer.parseInt(id);
            ArrayList idArray = new ArrayList();
            idArray.add(i_id);
            List<Integer> idList = (List<Integer>)idArray;
            //调用接口插入每日菜谱
            menuBaseDao.deleteFromMenu(idList);
            return AjaxObject.ok("删除成功！");
        }
        catch(Exception e)
        {
            return AjaxObject.error("删除失败！");
        }
    }


}

