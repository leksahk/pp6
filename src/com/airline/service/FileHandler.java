package com.airline.service;

import com.airline.model.*;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private static final String FILE = "fleet.txt";

    public static void save(AirlineFleet fleet) {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (Airplane p : fleet.getFleet()) {
                pw.println(serialize(p));
            }
            System.out.println("Дані збережено у файл: " + FILE);
        } catch (IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    public static void load(AirlineFleet fleet) {
        File file = new File(FILE);
        if (!file.exists()) {
            System.out.println("Файл не знайдено. Створено новий флот.");
            return;
        }
        try (Scanner sc = new Scanner(file)) {
            int loaded = 0;
            while (sc.hasNextLine()) {
                Airplane plane = deserialize(sc.nextLine());
                if (plane != null && fleet.addAirplane(plane)) loaded++;
            }
            System.out.println("Завантажено " + loaded + " літаків із файлу: " + FILE);
        } catch (IOException e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
        }
    }

    private static String serialize(Airplane p) {
        String type = p.getClass().getSimpleName();
        String base = String.format("%s;%s;%s;%d;%.2f;%.2f;%.2f",
                type, p.getModel(), p.getManufacturer(), p.getYearOfManufacture(),
                p.getMaxSpeed(), p.getFlightRange(), p.getFuelConsumption());
        if (p instanceof PassengerAirplane) {
            PassengerAirplane pa = (PassengerAirplane) p;
            return base + ";" + pa.getPassengerSeats();
        } else if (p instanceof CargoAirplane) {
            CargoAirplane ca = (CargoAirplane) p;
            return base + ";" + ca.getPayloadCapacity();
        } else if (p instanceof PrivateJet) {
            PrivateJet pj = (PrivateJet) p;
            return base + ";" + pj.getPassengers() + ";" + pj.getLuxuryLevel();
        } else if (p instanceof MilitaryAirplane) {
            MilitaryAirplane ma = (MilitaryAirplane) p;
            return base + ";" + ma.getWeaponLoad();
        } else {
            return base;
        }
    }

    private static Airplane deserialize(String line) {
        String[] parts = line.split(";");
        if (parts.length < 7) return null;
        String type = parts[0];
        String model = parts[1], manufacturer = parts[2];
        int year = Integer.parseInt(parts[3]);
        double speed = Double.parseDouble(parts[4].replace(',', '.')), range = Double.parseDouble(parts[5].replace(',', '.')), fuel = Double.parseDouble(parts[6].replace(',', '.'));

        switch (type) {
            case "PassengerAirplane":
                return new PassengerAirplane(model, manufacturer, year, speed, range, fuel, Integer.parseInt(parts[7]));
            case "CargoAirplane":
                return new CargoAirplane(model, manufacturer, year, speed, range, fuel, Double.parseDouble(parts[7]));
            case "PrivateJet":
                return new PrivateJet(model, manufacturer, year, speed, range, fuel, Integer.parseInt(parts[7]), Integer.parseInt(parts[8]));
            case "MilitaryAirplane":
                return new MilitaryAirplane(model, manufacturer, year, speed, range, fuel, Double.parseDouble(parts[7]));
            default:
                return null;
        }
    }
}