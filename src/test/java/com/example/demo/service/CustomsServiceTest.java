package com.example.demo.service;

import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomsServiceTest {

    @Mock
    private FerryService ferryService;
    @InjectMocks
    private CustomsService target;

    private static final Vehicle VEHICLE = Vehicle.builder()
            .type(VehicleType.BUS)
            .gas(0)
            .build();

    @Test
    public void getCustomsCheck() {
        assertFalse(VEHICLE.isWentThroughCustoms());

        target.customsCheckVehicle(VEHICLE);

        assertTrue(VEHICLE.isWentThroughCustoms());
    }

    @Test
    public void locationLogged() {
        target.customsCheckVehicle(VEHICLE);

        List<String> path = VEHICLE.getPath();
        assertEquals(CustomsService.LOCATION, path.get(path.size() - 1));
    }

    @Test
    public void sentToFerry() {
        target.customsCheckVehicle(VEHICLE);

        Mockito.verify(ferryService).loadVehicle(VEHICLE);
    }

}