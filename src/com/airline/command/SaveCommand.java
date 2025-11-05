package com.airline.command;

import com.airline.service.AirlineFleet;
import com.airline.service.FileHandler;

public class SaveCommand implements Command {
    private final AirlineFleet fleet;

    public SaveCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        FileHandler.save(fleet);
    }
}