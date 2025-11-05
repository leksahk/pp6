package com.airline.service;

import com.airline.model.*;
import java.util.*;

public class AirlineFleet {
    private final int maxAirplanes;
    private final List<Airplane> fleet = new ArrayList<>();

    public AirlineFleet(int maxAirplanes) {
        if (maxAirplanes <= 0) throw new IllegalArgumentException("Максимальна кількість > 0");
        this.maxAirplanes = maxAirplanes;
    }

    public boolean addAirplane(Airplane plane) {
        if (fleet.size() >= maxAirplanes) return false;
        fleet.add(plane);
        return true;
    }

    public boolean removeAirplane(String model) {
        return fleet.removeIf(p -> p.getModel().equalsIgnoreCase(model));
    }

    public Airplane findByModel(String model) {
        return fleet.stream()
                .filter(p -> p.getModel().equalsIgnoreCase(model))
                .findFirst().orElse(null);
    }

    public double getTotalPassengerCapacity() {
        return fleet.stream()
                .filter(p -> p instanceof PassengerAirplane || p instanceof PrivateJet)
                .mapToDouble(Airplane::calculateCapacity)
                .sum();
    }

    public double getTotalPayloadCapacity() {
        return fleet.stream()
                .filter(p -> p instanceof CargoAirplane || p instanceof MilitaryAirplane)
                .mapToDouble(Airplane::calculateCapacity)
                .sum();
    }

    public List<Airplane> sortByFlightRange() {
        return fleet.stream()
                .sorted(Comparator.comparingDouble(Airplane::getFlightRange).reversed())
                .toList();
    }

    public List<Airplane> findByFuelConsumption(double min, double max) {
        return fleet.stream()
                .filter(p -> p.getFuelConsumption() >= min && p.getFuelConsumption() <= max)
                .toList();
    }

    public List<PrivateJet> findByLuxuryLevel(int min, int max) {
        return fleet.stream()
                .filter(p -> p instanceof PrivateJet)
                .map(p -> (PrivateJet) p)
                .filter(j -> j.getLuxuryLevel() >= min && j.getLuxuryLevel() <= max)
                .toList();
    }

    public void generateReport() {
            System.out.println("ЗВІТ ПО ФЛОТУ");
            System.out.println("Кількість літаків: " + fleet.size() + " / " + maxAirplanes);
            System.out.printf("Загальна пасажиромісткість: %.0f осіб\n", getTotalPassengerCapacity());
            System.out.printf("Загальна вантажопідйомність: %.2f тонн\n", getTotalPayloadCapacity());
    }

    public List<Airplane> getFleet() {
        return Collections.unmodifiableList(fleet);
    }
    public void clear() {
        this.fleet.clear();
    }
}