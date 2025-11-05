package com.airline.command;

import com.airline.service.AirlineFleet;

public class DisplayCommand implements Command {
    private final AirlineFleet fleet;

    public DisplayCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        System.out.println("\nФлот авіакомпанії:");
        if (fleet.getFleet().isEmpty()) {
            System.out.println("Флот порожній.");
        } else {
            fleet.getFleet().forEach(System.out::println);
        }
    }
}