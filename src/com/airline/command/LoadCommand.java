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
        fleet.clear();
        FileHandler.load(fleet);
    }
}