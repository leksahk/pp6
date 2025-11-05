package com.airline.command;

import com.airline.model.*;
import com.airline.service.AirlineFleet;
import java.util.Scanner;

public class EditCommand implements Command {
    private final AirlineFleet fleet;
    private final Scanner scanner;

    public EditCommand(AirlineFleet fleet, Scanner scanner) {
        this.fleet = fleet;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введіть модель літака для редагування: ");
        String model = scanner.nextLine();
        Airplane plane = fleet.findByModel(model);
        if (plane == null) {
            System.out.println("Літак не знайдено.");
            return;
        }

        System.out.println("Знайдено: " + plane);
        System.out.print("Нова модель (або Enter): ");
        String newModel = scanner.nextLine();
        if (!newModel.isEmpty()) plane.setModel(newModel);

        System.out.print("Новий рік (або 0): ");
        int year = scanner.nextInt();
        if (year > 0) plane.setYearOfManufacture(year);

        System.out.print("Нова швидкість (або 0): ");
        double speed = scanner.nextDouble();
        if (speed > 0) plane.setMaxSpeed(speed);

        System.out.print("Нова дальність (або 0): ");
        double range = scanner.nextDouble();
        if (range > 0) plane.setFlightRange(range);

        System.out.print("Нова витрата пального (або 0): ");
        double fuel = scanner.nextDouble();
        if (fuel > 0) plane.setFuelConsumption(fuel);

        if (plane instanceof PassengerAirplane pa) {
            System.out.print("Нова кількість місць (або 0): ");
            int seats = scanner.nextInt();
            if (seats > 0) pa.setPassengerSeats(seats);
        } else if (plane instanceof CargoAirplane ca) {
            System.out.print("Нова вантажопідйомність (або 0): ");
            double payload = scanner.nextDouble();
            if (payload > 0) ca.setPayloadCapacity(payload);
        } else if (plane instanceof PrivateJet pj) {
            System.out.print("Нова кількість пасажирів (або 0): ");
            int pax = scanner.nextInt();
            if (pax > 0) pj.setPassengers(pax);
            System.out.print("Новий рівень розкоші (або 0): ");
            int lux = scanner.nextInt();
            if (lux > 0) pj.setLuxuryLevel(lux);
        } else if (plane instanceof MilitaryAirplane ma) {
            System.out.print("Новий вантаж боєприпасів (або 0): ");
            double load = scanner.nextDouble();
            if (load >= 0) ma.setWeaponLoad(load);
        }

        System.out.println("Літак оновлено: " + plane.getModel());
    }
}