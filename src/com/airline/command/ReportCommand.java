package com.airline.command;

import com.airline.service.AirlineFleet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ReportCommand.class);
    private final AirlineFleet fleet;

    public ReportCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        logger.info("Генерація звіту по флоту...");
        fleet.generateReport();
        logger.info("Звіт успішно згенеровано.");
    }
}