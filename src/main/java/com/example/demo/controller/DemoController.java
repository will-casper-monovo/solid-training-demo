package com.example.demo.controller;

import com.example.demo.model.AddVehicleRequest;
import com.example.demo.model.AppState;
import com.example.demo.model.Vehicle;
import com.example.demo.service.TerminalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
@AllArgsConstructor
public class DemoController {

    private final AppState appState;
    private final TerminalService terminalService;

    @PostMapping("/addVehicle")
    public ResponseEntity<Void> addVehicle(@RequestBody AddVehicleRequest addVehicleRequest) {
        Vehicle newVehicle = Vehicle.builder()
                .type(addVehicleRequest.getVehicleType())
                .gas(addVehicleRequest.getGas())
                .build();
        terminalService.checkinVehicle(newVehicle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/state")
    public ResponseEntity<AppState> getState() {
        return new ResponseEntity<>(appState, HttpStatus.OK);
    }

}
