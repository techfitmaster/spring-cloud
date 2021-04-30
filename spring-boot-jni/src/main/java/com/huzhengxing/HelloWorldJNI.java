package com.huzhengxing;

/**
 * @author - 2021/2/26 17:36 albert
 * @version - 2021/2/26 17:36 1.0.0
 * @file HelloWorldJNI
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
public class HelloWorldJNI {

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        HelloWorldJNI helloWorldJNI = new HelloWorldJNI();
        helloWorldJNI.sayHello();
        helloWorldJNI.add(1,2);
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    public native void sayHello();

    public native void  add(int a, int b);
}
