package com.airline.command;

import com.airline.model.Airplane;
import com.airline.service.AirlineFleet;
import java.util.Scanner;

public class SearchFuelCommand implements Command {
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
        System.out.println("\nРезультат пошуку за паливом:");
        if (result.isEmpty()) {
            System.out.println("Літаків не знайдено.");
        } else for (Airplane p : result) {
            System.out.println(p);
        }
        scanner.nextLine();
    }
}