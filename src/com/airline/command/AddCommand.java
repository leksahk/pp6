package com.airline.command;

import com.airline.model.*;
import com.airline.service.AirlineFleet;
import java.util.Scanner;

public class AddCommand implements Command {
    private final AirlineFleet fleet;
    private final Scanner scanner;

    public AddCommand(AirlineFleet fleet, Scanner scanner) {
        this.fleet = fleet;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Виберіть тип літака:");
        System.out.println("1. Пасажирський");
        System.out.println("2. Вантажний");
        System.out.println("3. Приватний джет");
        System.out.println("4. Військовий");
        System.out.print("Ваш вибір: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Виробник: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Рік випуску: ");
        int year = scanner.nextInt();
        System.out.print("Макс. швидкість (км/год): ");
        double maxSpeed = scanner.nextDouble();
        System.out.print("Дальність польоту (км): ");
        double flightRange = scanner.nextDouble();
        System.out.print("Витрати пального (л/км): ");
        double fuelConsumption = scanner.nextDouble();

        try {
            Airplane plane = null;
            switch (type) {
                case 1:
                    System.out.print("Кількість місць: ");
                    int seats = scanner.nextInt();
                    plane = new PassengerAirplane(model, manufacturer, year, maxSpeed, flightRange, fuelConsumption, seats);
                    break;
                case 2:
                    System.out.print("Вантажопідйомність (тонни): ");
                    double payload = scanner.nextDouble();
                    plane = new CargoAirplane(model, manufacturer, year, maxSpeed, flightRange, fuelConsumption, payload);
                    break;
                case 3:
                    System.out.print("Кількість пасажирів (1-10): ");
                    int passengers = scanner.nextInt();
                    System.out.print("Рівень розкоші (1-5): ");
                    int luxury = scanner.nextInt();
                    plane = new PrivateJet(model, manufacturer, year, maxSpeed, flightRange, fuelConsumption, passengers, luxury);
                    break;
                case 4:
                    System.out.print("Вантаж боєприпасів (тонни): ");
                    double weaponLoad = scanner.nextDouble();
                    plane = new MilitaryAirplane(model, manufacturer, year, maxSpeed, flightRange, fuelConsumption, weaponLoad);
                    break;
                default:
                    throw new IllegalArgumentException("Неправильний тип");
            }
            if (fleet.addAirplane(plane)) {
                System.out.println("Літак додано: " + plane.getModel());
            } else {
                System.out.println("Флот переповнений");
            }

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
        scanner.nextLine();
    }
}