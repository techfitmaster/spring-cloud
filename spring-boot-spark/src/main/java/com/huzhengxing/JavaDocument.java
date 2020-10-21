package com.huzhengxing;

import java.io.Serializable;

/**
 * @author 2020/10/20 14:59  zhengxing.hu
 * @version 1.0.0
 * @file JavaDocument
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@SuppressWarnings("serial")
public class JavaDocument implements Serializable {

    private long id;
    private String text;

    public JavaDocument(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }
}