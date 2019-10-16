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
        String message = "";
        String status = "B";
        int orderingCode = 4;
        if (scanNums == 1){
            message = status + orderingCode;
        }else if (scanNums == 2){
            message = "C";
        }
        String userId = "80234530";
        try {
            WebSocketServer.sendInfo(message,userId);
            scanNums++;
            if (scanNums == 3){
                scanNums = 1;
            }
        } catch (IOException e) {
            logger.error(userId+"#"+e.getMessage());
        }
    }
}
