package org.hackthon.letsdinner.core;

import org.hackthon.letsdinner.model.AjaxObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest req){
        AjaxObject r = new AjaxObject();
        //业务异常
        if(e instanceof BusinessException){
            r.put("code", ((BusinessException) e).getCode());
            r.put("msg", ((BusinessException) e).getMsg());
        }else{//系统异常
            r.put("code","500");
            r.put("msg","未知异常，请联系管理员");
        }

        logger.error("url: {}, msg: {}, stackTrace: {}", req.getRequestURL(), e.getMessage(), e.getStackTrace());

        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return r;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }
}
