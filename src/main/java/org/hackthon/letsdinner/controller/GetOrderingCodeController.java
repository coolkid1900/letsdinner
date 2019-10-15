package org.hackthon.letsdinner.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GetOrderingCodeController {
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/gerorderingcode")
    public String index(HttpServletRequest request, Model model)
    {
        String param = request.getParameter("key");
        return "请在手机上查看自己的取餐码。"+param;
    }
}
