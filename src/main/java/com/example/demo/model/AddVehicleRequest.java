package com.example.demo.model;

import lombok.Data;

@Data
public class AddVehicleRequest {
    private VehicleType vehicleType;
    private double gas;
}
