package com.airline.command;

import com.airline.service.AirlineFleet;
import com.airline.service.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoadCommand.class);
    private final AirlineFleet fleet;

    public LoadCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        logger.info("Спроба завантаження флоту з файлу");
        try {
            FileHandler.load(fleet);
        } catch (Exception e) {
            logger.error("Помилка при завантаженні: {}", e.getMessage());
        }
    }
}