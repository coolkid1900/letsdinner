package org.hackthon.letsdinner.controller;

import com.alibaba.fastjson.JSON;
import org.hackthon.letsdinner.core.WebSocketServer;
import org.hackthon.letsdinner.dao.TakeProcessDao;
import org.hackthon.letsdinner.model.JsonResult;
import org.hackthon.letsdinner.model.OrderNum;
import org.hackthon.letsdinner.model.OrderStatus;
import org.hackthon.letsdinner.model.PushMessage;
import org.hackthon.letsdinner.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class ScanQrCodeController {
    private static Logger logger = LoggerFactory.getLogger(ScanQrCodeController.class);

    @Autowired
    private TakeProcessDao takeProcessDao;

    private static final String STATUS_DEFAULT = "D";

    private static final String STATUS_IN = "I";

    private static final String STATUS_OUT = "O";

    private static final String STATUS_WAIT = "W";

    @RequestMapping("/scanqrcode")
    public void scanqrcode(HttpServletRequest request, Model model)
    {
        String key = request.getParameter("key");
        PushMessage pushMessage = new PushMessage();

        if (getOrderStatusByKey(key).equals(STATUS_DEFAULT)){
            if (setOrderStatus(key,STATUS_IN)){
                int orderNum = getValidOrderNumByKey(key);
                if (orderNum != -1){
                    pushMessage.setStatus(STATUS_IN);
                    pushMessage.setMessage("取餐号:"+orderNum);
                }else {
                    pushMessage.setStatus(STATUS_WAIT);
                    pushMessage.setMessage("请稍后!");
                }
            }
        } else if(getOrderStatusByKey(key).equals(STATUS_IN)){
            if (setOrderStatus(key,STATUS_OUT)){
                pushMessage.setStatus(STATUS_OUT);
                pushMessage.setMessage("请用餐~");
            }
        }else{
            pushMessage.setStatus(STATUS_OUT);
            pushMessage.setMessage("弄啥咧？");
        }

        String pushMessageJson = JSON.toJSONString(pushMessage);
        try {
            WebSocketServer.sendInfo(pushMessageJson,key);
        } catch (IOException e) {
            logger.error(key+"#"+e.getMessage());
        }
    }

    private String getOrderStatusByKey(String key) {
        JsonResult jsonResult = BaseUtils.parseJson(takeProcessDao.getDishStatus(key));
        String orderStatusJson = "";
        if (jsonResult.getCode() == 0){
            orderStatusJson = jsonResult.getData().toJSONString();
        }
        OrderStatus orderStatus = JSON.parseObject(orderStatusJson,OrderStatus.class);
        return orderStatus.getStatus();
    }

    private int getValidOrderNumByKey(String key) {
        JsonResult jsonResult = BaseUtils.parseJson(takeProcessDao.takeOneDish(key));
        String orderNumJson = "";
        if (jsonResult.getCode() == 0){
            orderNumJson = jsonResult.getData().toJSONString();
        }
        else if (jsonResult.getCode() == 404)
        {
            return -1;
        }
        OrderNum orderNum = JSON.parseObject(orderNumJson, OrderNum.class);
        return orderNum.getId();
    }

    private boolean setOrderStatus(String key, String status) {
        JsonResult jsonResult = BaseUtils.parseJson(takeProcessDao.updateDishStatus(key,status));
        boolean setStatus = false;
        if (jsonResult.getCode() == 0){
            setStatus = true;
        }
        return setStatus;
    }
}
