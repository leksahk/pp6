package com.airline.command;

import com.airline.model.PrivateJet;
import com.airline.service.AirlineFleet;
import java.util.Scanner;

public class SearchLuxuryCommand implements Command {
    private final AirlineFleet fleet;
    private final Scanner scanner;

    public SearchLuxuryCommand(AirlineFleet fleet, Scanner scanner) {
        this.fleet = fleet;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Мін. рівень розкоші (1-5): ");
        int min = scanner.nextInt();
        System.out.print("Макс. рівень розкоші (1-5): ");
        int max = scanner.nextInt();

        var result = fleet.findByLuxuryLevel(min, max);
        System.out.println("\nПриватні джети за рівнем розкоші");
        if (result.isEmpty()) {
            System.out.println("Джетів не знайдено.");
        } else {
            for (PrivateJet jet : result) {
                System.out.println(jet);
            }
        }
        scanner.nextLine();
    }
}