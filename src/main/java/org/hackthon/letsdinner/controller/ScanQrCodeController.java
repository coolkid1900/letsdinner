package org.hackthon.letsdinner.controller;

import org.hackthon.letsdinner.core.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class ScanQrCodeController {
    private static Logger logger = LoggerFactory.getLogger(ScanQrCodeController.class);

    private int scanNums = 1;

    @RequestMapping("/scanqrcode")
    public void scanqrcode(HttpServletRequest request, Model model)
    {
        String key = request.getParameter("key");
        String message;
        String orderNum;
        if (getOrderStatusByKey(key).equals("A")){
            orderNum = getValidOrderNumByKey(key);
            message = "B" + orderNum;
        }else{
            message = "C";
        }
        try {
            WebSocketServer.sendInfo(message,key);
        } catch (IOException e) {
            logger.error(key+"#"+e.getMessage());
        }
    }

    private String getOrderStatusByKey(String key) {
        String orderStatus;
        orderStatus = "A";
        return orderStatus;
    }

    private String getValidOrderNumByKey(String key) {
        String orderNum;
        orderNum ="4";
        return orderNum;
    }
}
