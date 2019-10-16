package org.hackthon.letsdinner.controller;

import org.hackthon.letsdinner.core.BusinessException;
import org.hackthon.letsdinner.model.AjaxObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController
{
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model)
    {
        //注释
        String param = request.getParameter("param");
        model.addAttribute("message", param);
        return "index";
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
    @RequestMapping("/ajax")
    public AjaxObject ajax(HttpServletRequest request, Model model)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("user", "linxu");
        return AjaxObject.ok(map);
    }
    /**
     * 请求页面失败示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/errorHtml")
    public String errorHtml(HttpServletRequest request, Model model)
    {
        String param = request.getParameter("param");
        if (param == null)
        {
            throw new BusinessException("param不能为null");
        }
        return "error";
    }
    /**
     * ajax请求json失败示例，需要添加两个注解@ResponseBody和@RequestMapping
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的json对象
     * json格式如下：
     * {
     *     code：500，
     *     msg：“获取列表成功”，
     *     data：{
     *     }
     * }
     */
    @ResponseBody
    @RequestMapping("/errorAjax")
    public AjaxObject errorAjax(HttpServletRequest request, Model model)
    {
        String param = request.getParameter("param");
        if (param == null)
        {
            // 在上述成功页面处理方法中执行参数检查
            throw new BusinessException("param不能为null");
        }
        return AjaxObject.error("执行错误");
    }
}
