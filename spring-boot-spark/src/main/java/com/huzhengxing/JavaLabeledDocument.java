package com.huzhengxing;

/**
 * @author 2020/10/20 15:00  zhengxing.hu
 * @version 1.0.0
 * @file JavaLabeledDocument
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */

import java.io.Serializable;

/**
 * Labeled instance type, Spark SQL can infer schema from Java Beans.
 */
@SuppressWarnings("serial")
public class JavaLabeledDocument extends JavaDocument implements Serializable {

    private double label;

    public JavaLabeledDocument(long id, String text, double label) {
        super(id, text);
        this.label = label;
    }

    public double getLabel() {
        return this.label;
    }
}
