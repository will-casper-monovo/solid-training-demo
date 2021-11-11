package com.example.demo.service;

import com.example.demo.model.AppState;
import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TerminalServiceTest {

    @Spy
    private AppState appState;
    @Mock
    private GasStationService gasStationService;
    @Mock
    private CustomsService customsService;
    @Mock
    private FerryService ferryService;
    @InjectMocks
    private TerminalService target;

    private static Vehicle VEHICLE;

    @BeforeEach
    public void setup() {
        VEHICLE = Vehicle.builder()
            .type(VehicleType.BUS)
            .gas(0)
            .build();
    }

    @Test
    public void locationLogged() {
        target.checkinVehicle(VEHICLE);

        List<String> path = VEHICLE.getPath();
        assertEquals(TerminalService.LOCATION, path.get(path.size() - 1));
    }

    @Test
    public void paymentsMade() {
        assertEquals(0, appState.getTotalIncome());
        assertEquals(0, appState.getTotalIncome());

        target.checkinVehicle(VEHICLE);

        assertEquals(VEHICLE.getType().getTicketPrice(), appState.getTotalIncome());
        assertEquals(VEHICLE.getType().getTicketPrice() * 0.1, appState.getEmployeeIncome());

        target.checkinVehicle(VEHICLE);
        target.checkinVehicle(VEHICLE);

        assertEquals(VEHICLE.getType().getTicketPrice() * 3, appState.getTotalIncome());
        assertEquals(VEHICLE.getType().getTicketPrice() * 0.1 * 3, appState.getEmployeeIncome());
    }

    @Test
    public void highGas_noCustoms_sentToFerry() {
        Vehicle v1 = VEHICLE.withGas(1).withType(VehicleType.BUS);
        Vehicle v2 = VEHICLE.withGas(1).withType(VehicleType.CAR);

        target.checkinVehicle(v1);
        target.checkinVehicle(v2);

        Mockito.verifyNoInteractions(gasStationService);
        Mockito.verifyNoInteractions(customsService);
        Mockito.verify(ferryService).loadVehicle(v1);
        Mockito.verify(ferryService).loadVehicle(v2);
    }

    @Test
    public void highGas_withCustoms_sentToCustoms() {
        Vehicle v1 = VEHICLE.withGas(1).withType(VehicleType.TRUCK);
        Vehicle v2 = VEHICLE.withGas(1).withType(VehicleType.VAN);

        target.checkinVehicle(v1);
        target.checkinVehicle(v2);

        Mockito.verifyNoInteractions(gasStationService);
        Mockito.verifyNoInteractions(ferryService);
        Mockito.verify(customsService).customsCheckVehicle(v1);
        Mockito.verify(customsService).customsCheckVehicle(v2);
    }

    @Test
    public void lowGas_alwaysSentToGasStation() {
        Vehicle v1 = VEHICLE.withGas(0.01).withType(VehicleType.BUS);
        Vehicle v2 = VEHICLE.withGas(0.01).withType(VehicleType.CAR);
        Vehicle v3 = VEHICLE.withGas(0.01).withType(VehicleType.TRUCK);
        Vehicle v4 = VEHICLE.withGas(0.01).withType(VehicleType.VAN);

        target.checkinVehicle(v1);
        target.checkinVehicle(v2);
        target.checkinVehicle(v3);
        target.checkinVehicle(v4);

        Mockito.verifyNoInteractions(customsService);
        Mockito.verifyNoInteractions(ferryService);
        Mockito.verify(gasStationService).refillVehicle(v1);
        Mockito.verify(gasStationService).refillVehicle(v2);
        Mockito.verify(gasStationService).refillVehicle(v3);
        Mockito.verify(gasStationService).refillVehicle(v4);
    }

}