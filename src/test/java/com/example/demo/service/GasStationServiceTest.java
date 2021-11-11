package com.example.demo.service;

import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GasStationServiceTest {

    @Mock
    CustomsService customsService;
    @Mock
    FerryService ferryService;
    @InjectMocks
    GasStationService target;

    private static Vehicle VEHICLE;

    @BeforeEach
    public void setup() {
        VEHICLE = Vehicle.builder()
                .type(VehicleType.BUS)
                .gas(0)
                .build();
    }

    @Test
    public void fillsGas() {
        assertEquals(0, VEHICLE.getGas());

        target.refillVehicle(VEHICLE);

        assertEquals(1, VEHICLE.getGas());
    }

    @Test
    public void locationLogged() {
        target.refillVehicle(VEHICLE);

        List<String> path = VEHICLE.getPath();
        assertEquals(GasStationService.LOCATION, path.get(path.size() - 1));
    }

    @Test
    public void noCustoms() {
        Vehicle v1 = VEHICLE.withType(VehicleType.BUS);
        Vehicle v2 = VEHICLE.withType(VehicleType.CAR);

        target.refillVehicle(v1);
        target.refillVehicle(v2);

        Mockito.verifyNoInteractions(customsService);
        Mockito.verify(ferryService).loadVehicle(v1);
        Mockito.verify(ferryService).loadVehicle(v2);
    }

    @Test
    public void withCustoms() {
        Vehicle v1 = VEHICLE.withType(VehicleType.TRUCK);
        Vehicle v2 = VEHICLE.withType(VehicleType.VAN);

        target.refillVehicle(v1);
        target.refillVehicle(v2);

        Mockito.verifyNoInteractions(ferryService);
        Mockito.verify(customsService).customsCheckVehicle(v1);
        Mockito.verify(customsService).customsCheckVehicle(v2);
    }

}