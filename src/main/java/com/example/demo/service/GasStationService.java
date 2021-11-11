package com.example.demo.service;

import com.example.demo.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GasStationService {

    private final CustomsService customsService;
    private final FerryService ferryService;

    public static final String LOCATION = "GAS_STATION";

    public void refillVehicle(Vehicle vehicle) {
        vehicle.getPath().add(LOCATION);
        vehicle.setGas(1);

        if (vehicle.getType().shouldGoThroughCustoms()) {
            customsService.customsCheckVehicle(vehicle);
        } else {
            ferryService.loadVehicle(vehicle);
        }
    }
}
