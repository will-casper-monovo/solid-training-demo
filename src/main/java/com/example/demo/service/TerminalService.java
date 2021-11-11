package com.example.demo.service;

import com.example.demo.model.AppState;
import com.example.demo.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TerminalService {

    private final AppState appState;
    private final GasStationService gasStationService;
    private final CustomsService customsService;
    private final FerryService ferryService;

    public static final String LOCATION = "TERMINAL";
    public static final double LOW_GAS_THRESHOLD = 0.1;
    public static final double EMPLOYEE_CUT = 0.1;

    public void checkinVehicle(Vehicle vehicle) {
        double ticketPrice = vehicle.getType().getTicketPrice();
        appState.setTotalIncome(appState.getTotalIncome() + ticketPrice);
        appState.setEmployeeIncome(appState.getEmployeeIncome() + ticketPrice * EMPLOYEE_CUT);

        vehicle.getPath().add(LOCATION);

        if (vehicle.getGas() <= LOW_GAS_THRESHOLD) {
            gasStationService.refillVehicle(vehicle);
        } else if (vehicle.getType().shouldGoThroughCustoms()) {
            customsService.customsCheckVehicle(vehicle);
        } else {
            ferryService.loadVehicle(vehicle);
        }
    }
}
