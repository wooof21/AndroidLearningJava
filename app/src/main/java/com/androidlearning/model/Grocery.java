package com.androidlearning.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Grocery {
    private int imgId;
    private String title;
    private String description;
}
