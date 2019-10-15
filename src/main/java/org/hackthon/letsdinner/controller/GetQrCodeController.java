package org.hackthon.letsdinner.controller;

import org.hackthon.letsdinner.LetsdinnerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class GetQrCodeController {
    private static Logger logger = LoggerFactory.getLogger(LetsdinnerApplication.class);
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/getqrcode")
    public String index(HttpServletRequest request, Model model)
    {
        String test="12";
        String host = "";
        String port = "8080";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }
        String qrcodeUrl = "http://" + host + ":" + port + "/gerorderingcode?key=" + test;
        System.out.println(qrcodeUrl);
        model.addAttribute("qrcodeUrl", qrcodeUrl);
        return "getQrMainPage";
    }
}
