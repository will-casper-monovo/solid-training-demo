package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@With
public class Vehicle {

    private final VehicleType type;
    private double gas;
    private boolean wentThroughCustoms = false;
    private final List<String> path = new LinkedList<>();

}
