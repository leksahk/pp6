package com.airline.service;

public class TestEmailLog {
    public static void main(String[] args) {
        AirlineFleet fleet = new AirlineFleet(50);

        FileHandler.load(fleet);

        System.out.println("Завантажено " + fleet.getFleet().size() + " літаків");
    }
}


