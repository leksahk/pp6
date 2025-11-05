package com.airline.command;

import com.airline.service.AirlineFleet;
import java.util.Scanner;

public class RemoveCommand implements Command {
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
            System.out.println("Літак видалено: " + model);
        } else {
            System.out.println("Літак не знайдено: " + model);
        }
    }
}