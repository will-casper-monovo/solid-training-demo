package com.example.demo.service;

import com.example.demo.model.AppState;
import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleSize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class FerryService {

    private final AppState appState;
    private int vehiclesOnSmallFerry = 0;
    private int vehiclesOnLargeFerry = 0;

    public static final int SMALL_FERRY_CAPACITY = 8;
    public static final int LARGE_FERRY_CAPACITY = 6;
    public static final String LOCATION = "FERRY";

    public void loadVehicle(Vehicle vehicle) {
        vehicle.getPath().add(LOCATION);

        VehicleSize size = vehicle.getType().getSize();
        switch (size) {
            case SMALL:
                loadVehicleOntoSmallFerry();
                break;
            case LARGE:
                loadVehicleOntoLargeFerry();
                break;
            default:
                throw new UnsupportedOperationException();
        }

        appState.getVehicles().add(vehicle);
    }

    private void loadVehicleOntoSmallFerry() {
        vehiclesOnSmallFerry += 1;
        if (vehiclesOnSmallFerry % SMALL_FERRY_CAPACITY == 0) {
            appState.setSmallFerriesDispatched(appState.getSmallFerriesDispatched() + 1);
            vehiclesOnSmallFerry = 0;
        }
    }

    private void loadVehicleOntoLargeFerry() {
        vehiclesOnLargeFerry += 1;
        if (vehiclesOnLargeFerry % LARGE_FERRY_CAPACITY == 0) {
            appState.setLargeFerriesDispatched(appState.getLargeFerriesDispatched() + 1);
            vehiclesOnLargeFerry = 0;
        }
    }

}
