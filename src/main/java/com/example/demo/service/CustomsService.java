package com.example.demo.service;

import com.example.demo.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomsService {

    private final FerryService ferryService;

    public static final String LOCATION = "CUSTOMS";

    public void customsCheckVehicle(Vehicle vehicle) {
        vehicle.getPath().add(LOCATION);

        vehicle.setWentThroughCustoms(true);

        ferryService.loadVehicle(vehicle);
    }

}
