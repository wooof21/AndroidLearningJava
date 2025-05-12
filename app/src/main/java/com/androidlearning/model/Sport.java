package com.androidlearning.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sport {

    private String sportTitle;
    private int sportImg;
}
