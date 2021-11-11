package com.example.demo.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class AppState {

    private double totalIncome = 0;
    private double employeeIncome = 0;
    private List<Vehicle> vehicles = new LinkedList<>();
    private int smallFerriesDispatched = 0;
    private int largeFerriesDispatched = 0;
}
