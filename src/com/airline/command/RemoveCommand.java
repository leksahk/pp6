package com.airline.command;

import com.airline.service.AirlineFleet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RemoveCommand.class);
    private final AirlineFleet fleet;
    private final Scanner scanner;

    public RemoveCommand(AirlineFleet fleet, Scanner scanner) {
        this.fleet = fleet;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введіть модель літака для видалення: ");
        String model = scanner.nextLine();
        if (fleet.removeAirplane(model)) {
            logger.info("Літак видалено: " + model);
        } else {
            logger.warn("Літак не знайдено: " + model);
        }
    }
}