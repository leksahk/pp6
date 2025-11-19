package com.airline.command;

import com.airline.service.AirlineFleet;
import com.airline.service.FileHandler;

public class LoadCommand implements Command {
    private final AirlineFleet fleet;

    public LoadCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        System.out.println("Спроба завантаження флоту з файлу");
        try {
            FileHandler.load(fleet);
        } catch (Exception e) {
            System.out.println("Помилка при завантаженні: " + e.getMessage());
        }
    }
}
