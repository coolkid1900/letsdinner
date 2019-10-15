package org.hackthon.letsdinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebSocketTestController {
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/websockettest")
    public String websockettest(HttpServletRequest request, Model model)
    {
        return "websockettest";
    }

}
