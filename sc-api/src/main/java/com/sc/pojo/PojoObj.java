package com.sc.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class PojoObj {
    private String name;
    private Integer age;
}
