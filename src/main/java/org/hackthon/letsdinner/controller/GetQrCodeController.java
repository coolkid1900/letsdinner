package org.hackthon.letsdinner.controller;

import org.hackthon.letsdinner.core.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GetQrCodeController {
    private static Logger logger = LoggerFactory.getLogger(GetQrCodeController.class);

    @Autowired
    private ServerConfig serverConfig;

    private String getUrl() {
        return  serverConfig.getUrl();
    }

    private String getWebsocketUrl() {
        return  serverConfig.getWebsocketUrl();
    }
    /**
     * 请求页面成功示例
     * @param request 请求数据对象
     * @param model 返回页面模型
     * @return 返回的页面名称，去除.html后缀
     */
    @RequestMapping("/getqrcode")
    public String getqrcode(HttpServletRequest request, Model model)
    {
        String key = request.getParameter("key");
        String qrcodeUrl = getUrl() + "/scanqrcode?key=" + key;
        String websocketUrl = getWebsocketUrl() + "/websocket/" + key;
        model.addAttribute("qrcodeUrl", qrcodeUrl);
        model.addAttribute("websocketUrl",websocketUrl);
        return "getQrMainPage";
    }
}
