package org.hackthon.letsdinner.controller;

import org.hackthon.letsdinner.LetsdinnerApplication;
import org.hackthon.letsdinner.core.BusinessException;
import org.hackthon.letsdinner.model.AjaxObject;
import org.hackthon.letsdinner.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

@Controller
@RequestMapping("/Restaurant")
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
    @RequestMapping("/uploadPage")
    public String uploadPage(HttpServletRequest request, Model model)
    {
        return "restaurant";
    }

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, @RequestParam("file")MultipartFile file, Model model) throws IOException {
        try
        {
            //根据时间戳创建新的文件名
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //拼接文件名
            //String desFileName = request.getServletContext().getRealPath("") + "upLoaded"
                   // + File.separator + fileName;
            //String desFileName = LetsdinnerApplication.class.getResource("/static/image/") + "upLoaded"
                    //+ File.separator + fileName;
            String desFileName = "C:/letsdinner/letsdinner/target/classes/static/images/upLoaded"
                    + File.separator + fileName;
            File destFile = new File(desFileName);
            if(!destFile.exists())
            {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            model.addAttribute("fileName", fileName);
        }
        catch(FileSystemNotFoundException e)
        {
            e.printStackTrace();
            return "上传失败，" + e.getMessage();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "上传失败，" + e.getMessage();
        }


        return "上传成功";
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
    @RequestMapping("/show")
    public AjaxObject ajax(HttpServletRequest request, Model model)
    {
        Map<String, Object> map = new HashMap<>();
        File file= new File("file:C:/letsdinner/letsdinner/target/classes/static/images/upLoaded/1571229707528bcs.jpg");
        //string imagePath = resourceLoader.getResource("file:" + "C:/letsdinner/letsdinner/target/classes/static/image/upLoaded/1571229707528bcs.jpg");
        String imgPath = file.getAbsolutePath();
        map.put("imagePath", imgPath);
        return AjaxObject.ok(map);
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
        String testFileName="C:/letsdinner/letsdinner/target/classes/static/images/cookery-book/bcs.jpg";

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

        map.put("total", 2);
        map.put("rows", cookList);
        return map;
    }

}

class cookBean
{
    public String id;
    public String name;
    public String price;
    public String image;
}
