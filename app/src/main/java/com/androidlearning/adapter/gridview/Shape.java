package com.androidlearning.adapter.gridview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Shape {

    private int shapeImgId;
    private String shapeName;
}
