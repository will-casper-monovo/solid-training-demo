package com.example.demo.service;

import com.example.demo.model.AppState;
import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class FerryServiceTest {

    @Spy
    AppState appState;
    @InjectMocks
    FerryService target;

    private static final Vehicle VEHICLE = Vehicle.builder()
            .type(VehicleType.BUS)
            .gas(0)
            .build();

    @Test
    public void largeFerriesDispatched() {
        assertEquals(0, appState.getLargeFerriesDispatched());

        // load 5 large vehicles
        IntStream.range(0, 5).forEach(i -> target.loadVehicle(VEHICLE));

        assertEquals(0, appState.getLargeFerriesDispatched());
        assertEquals(5, target.getVehiclesOnLargeFerry());

        // load another 5 large vehicles
        IntStream.range(0, 5).forEach(i -> target.loadVehicle(VEHICLE));

        assertEquals(1, appState.getLargeFerriesDispatched());
        assertEquals(4 ,target.getVehiclesOnLargeFerry());

        // load another 10 large vehicles
        IntStream.range(0, 10).forEach(i -> target.loadVehicle(VEHICLE));

        assertEquals(3, appState.getLargeFerriesDispatched());
        assertEquals(2, target.getVehiclesOnLargeFerry());
    }

    @Test
    public void smallFerriesDispatched() {
        Vehicle v = VEHICLE.withType(VehicleType.CAR);
        assertEquals(0, appState.getSmallFerriesDispatched());

        // load 5 small vehicles
        IntStream.range(0, 5).forEach(i -> target.loadVehicle(v));

        assertEquals(0, appState.getSmallFerriesDispatched());
        assertEquals(5, target.getVehiclesOnSmallFerry());

        // load another 5 small vehicles
        IntStream.range(0, 5).forEach(i -> target.loadVehicle(v));

        assertEquals(1, appState.getSmallFerriesDispatched());
        assertEquals(2 ,target.getVehiclesOnSmallFerry());

        // load another 10 small vehicles
        IntStream.range(0, 10).forEach(i -> target.loadVehicle(v));

        assertEquals(2, appState.getSmallFerriesDispatched());
        assertEquals(4, target.getVehiclesOnSmallFerry());
    }

    @Test
    public void locationLogged() {
        target.loadVehicle(VEHICLE);

        List<String> path = VEHICLE.getPath();
        assertEquals(FerryService.LOCATION, path.get(path.size() - 1));
    }

    @Test
    public void vehicleAddedToState() {
        assertEquals(0, appState.getVehicles().size());

        target.loadVehicle(VEHICLE);

        assertEquals(1, appState.getVehicles().size());
        assertEquals(VEHICLE, appState.getVehicles().get(0));
    }

}