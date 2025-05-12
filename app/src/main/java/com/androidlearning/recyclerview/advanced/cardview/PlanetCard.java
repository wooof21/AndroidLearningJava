package com.androidlearning.recyclerview.advanced.cardview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanetCard {

    private String planetName;
    private int distanceFromSun;
    private int gravity;
    private int diameter;
}
