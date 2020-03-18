package com.huzhengxing;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private String id;

    private String name;

    private Integer age;

    private String sex;
}
