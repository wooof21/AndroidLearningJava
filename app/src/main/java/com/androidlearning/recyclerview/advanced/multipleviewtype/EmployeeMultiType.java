package com.androidlearning.recyclerview.advanced.multipleviewtype;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EmployeeMultiType {

    private String empName;
    private String address;
}
