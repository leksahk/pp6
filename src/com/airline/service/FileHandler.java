package com.airline.service;

import com.airline.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.mail.MessagingException;

import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);
    private static final String FILE = "fleett.txt";


    private static final EmailSender sender = new EmailSender(
            "rizze7778@gmail.com",
            "ugojtpsrqjkojqsf"
    );

    public static void save(AirlineFleet fleet) {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (Airplane p : fleet.getFleet()) {
                pw.println(serialize(p));
            }
            logger.info("Дані збережено у файл: " + FILE);
        } catch (IOException e) {
            logger.error("Помилка збереження: " + e.getMessage(), e);
            try {
                sender.sendError("rizze7778@gmail.com", "Помилка збереження у файл", e, "FileHandler.save");
            } catch (MessagingException ex) {
                System.err.println("Не вдалося надіслати email: " + ex.getMessage());
            }
        }
    }

public static void load(AirlineFleet fleet) {
    File file = new File(FILE);

    if (!file.exists()) {
        logger.error("Файл не знайдено: " + FILE);
        try {
            sender.sendError("rizze7778@gmail.com", "Файл не знайдено: " + FILE, new FileNotFoundException(FILE), "FileHandler.load");
        } catch (MessagingException ex) {
            System.err.println("Не вдалося надіслати email: " + ex.getMessage());
        }
        System.out.println("ПОМИЛКА: Файл даних '" + FILE + "' не знайдено. Флот не завантажено.");
        return;
    }

    try (Scanner sc = new Scanner(file)) {
        int loaded = 0;
        while (sc.hasNextLine()) {
            try {
                Airplane plane = deserialize(sc.nextLine());
                if (plane != null && fleet.addAirplane(plane)) loaded++;
            } catch (Exception e) {
                logger.error("Помилка десеріалізації рядка: " + e.getMessage(), e);
                try {
                    sender.sendError("rizze7778@gmail.com", "Помилка десеріалізації fleet.txt", e, "FileHandler.load");
                } catch (MessagingException ex) {
                    System.err.println("Не вдалося надіслати email: " + ex.getMessage());
                }
            }
        }
        logger.info("Завантажено " + loaded + " літаків із файлу: " + FILE);
    } catch (IOException e) {
        logger.error("Помилка читання файлу: " + e.getMessage(), e);
        try {
            sender.sendError("rizze7778@gmail.com", "Помилка читання fleet.txt", e, "FileHandler.load");
        } catch (MessagingException ex) {
            System.err.println("Не вдалося надіслати email: " + ex.getMessage());
        }
    }
}


    static String serialize(Airplane p) {
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

    static Airplane deserialize(String line) {
        String[] parts = line.split(";");
        if (parts.length < 7) throw new IllegalArgumentException("Некоректний формат рядка: " + line);

        String type = parts[0];
        String model = parts[1], manufacturer = parts[2];
        int year = Integer.parseInt(parts[3]);
        double speed = Double.parseDouble(parts[4].replace(',', '.'));
        double range = Double.parseDouble(parts[5].replace(',', '.'));
        double fuel = Double.parseDouble(parts[6].replace(',', '.'));

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
                throw new IllegalArgumentException("Невідомий тип літака: " + type);
        }
    }
}
