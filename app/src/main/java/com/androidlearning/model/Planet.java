package com.androidlearning.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Planet {

    private int planetImg;
    private String planetName;
    private String moonCount;

}
