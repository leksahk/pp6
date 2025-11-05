package com.airline.command;

import com.airline.service.AirlineFleet;

public class ReportCommand implements Command {
    private final AirlineFleet fleet;

    public ReportCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        fleet.generateReport();
    }
}