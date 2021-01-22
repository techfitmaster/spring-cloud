package com.huzhengxing.springbooti18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author 2020/11/5 09:27  zhengxing.hu
 * @version 1.0.0
 * @file Contoller
 * @brief
 * @par
 * @warning
 */
@RestController
public class Controller {

    @Autowired
    MessageSource messageSource;


    @RequestMapping("/hello")
    public String hello(String key) {
        return get(key);
    }

    /**
     * 获取单个国际化翻译值
     */
    public String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}
