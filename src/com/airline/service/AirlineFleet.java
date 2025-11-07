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
        Iterator<Airplane> iterator = fleet.iterator();
        while (iterator.hasNext()) {
            Airplane plane = iterator.next();
            if (plane.getModel().equalsIgnoreCase(model)) {
                iterator.remove();
                return true; //знайшли і видалили
            }
        }
        return false;
    }

    public Airplane findByModel(String model) {
        for (Airplane plane : fleet) {
            if (plane.getModel().equalsIgnoreCase(model)) {
                return plane;
            }
        }
        return null;
    }

    public double getTotalPassengerCapacity() {
        double totalCapacity = 0;
        for (Airplane plane : fleet) {
            if (plane instanceof PassengerAirplane || plane instanceof PrivateJet) {
                totalCapacity += plane.calculateCapacity();
            }
        }
        return totalCapacity;
    }

    public double getTotalPayloadCapacity() {
        double totalCapacity = 0;
        for (Airplane plane : fleet) {
            if (plane instanceof CargoAirplane || plane instanceof MilitaryAirplane) {
                totalCapacity += plane.calculateCapacity();
            }
        }
        return totalCapacity;
    }

    public List<Airplane> sortByFlightRange() {
        List<Airplane> sortedList = new ArrayList<>(fleet);
        //анонімний клас Comparator
        Comparator<Airplane> byRange = new Comparator<Airplane>() {
            @Override
            public int compare(Airplane a1, Airplane a2) {
                return Double.compare(a2.getFlightRange(), a1.getFlightRange());
            }
        };
        Collections.sort(sortedList, byRange);
        return sortedList;
    }

    public List<Airplane> findByFuelConsumption(double min, double max) {
        List<Airplane> result = new ArrayList<>();
        for (Airplane plane : fleet) {
            if (plane.getFuelConsumption() >= min && plane.getFuelConsumption() <= max) {
                result.add(plane);
            }
        }
        return result;
    }

    public List<PrivateJet> findByLuxuryLevel(int min, int max) {
        List<PrivateJet> result = new ArrayList<>();
        for (Airplane plane : fleet) {
            if (plane instanceof PrivateJet) {
                PrivateJet jet = (PrivateJet) plane;
                if (jet.getLuxuryLevel() >= min && jet.getLuxuryLevel() <= max) {
                    result.add(jet);
                }
            }
        }
        return result;
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