package com.airline.command;

import com.airline.model.Airplane;
import com.airline.service.AirlineFleet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchFuelCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SearchFuelCommand.class);
    private final AirlineFleet fleet;
    private final Scanner scanner;

    public SearchFuelCommand(AirlineFleet fleet, Scanner scanner) {
        this.fleet = fleet;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Мін. витрата пального (л/км): ");
        double min = scanner.nextDouble();
        System.out.print("Макс. витрата пального (л/км): ");
        double max = scanner.nextDouble();

        var result = fleet.findByFuelConsumption(min, max);
        logger.info("\nРезультат пошуку за паливом:");
        if (result.isEmpty()) {
            System.out.println("Літаків не знайдено.");
        } else{
            logger.info("Знайдено {} літаків.", result.size());
            for (Airplane p : result) {
                logger.info("{}", p);
            }
        }
        scanner.nextLine();
    }
}