package com.airline.service;

import com.airline.model.Airplane;
import com.airline.model.CargoAirplane;
import com.airline.model.PassengerAirplane;
import com.airline.model.PrivateJet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class AirlineFleetTest {
    private AirlineFleet fleet;

    private PassengerAirplane boeing;
    private CargoAirplane mriya;
    private PrivateJet gulfstream;

    @BeforeEach
    void setUp() {
        fleet = new AirlineFleet(5); // Створюємо чистий флот

        boeing = new PassengerAirplane("Boeing 737", "Boeing", 2020, 900, 12000, 0.8, 180);
        mriya = new CargoAirplane("An-225 Mriya", "Antonov", 1988, 850, 15000, 2.0, 250);
        gulfstream = new PrivateJet("G650", "Gulfstream", 2018, 1100, 13000, 0.9, 8, 5);
    }

    @Test
    void testAddAirplaneSuccessfully() {
        boolean result = fleet.addAirplane(boeing);

        assertTrue(result);
        assertEquals(1, fleet.getFleet().size());
        assertEquals("Boeing 737", fleet.getFleet().get(0).getModel());
    }

    @Test
    void testAddAirplaneFails() {
        fleet.addAirplane(boeing);
        fleet.addAirplane(mriya);
        fleet.addAirplane(gulfstream);
        fleet.addAirplane(new PassengerAirplane("A320", "Airbus", 2021, 870, 11000, 0.7, 150));
        fleet.addAirplane(new PassengerAirplane("A321", "Airbus", 2021, 870, 11000, 0.7, 150));

        assertEquals(5, fleet.getFleet().size()); // Флот повний

        //намагаємось додати 6-й літак
        PassengerAirplane extraPlane = new PassengerAirplane("Extra", "Extra", 2025, 1, 1, 1, 1);
        boolean result = fleet.addAirplane(extraPlane);

        assertFalse(result);
        assertEquals(5, fleet.getFleet().size());
    }

    @Test
    void testRemoveAirplane() {
        fleet.addAirplane(boeing);
        assertEquals(1, fleet.getFleet().size());

        boolean result = fleet.removeAirplane("Boeing 737");

        assertTrue(result);
        assertEquals(0, fleet.getFleet().size());
    }

    @Test
    void testRemoveAirplaneIgnoreCase() {
        fleet.addAirplane(boeing);
        //з маленької літери
        boolean result = fleet.removeAirplane("boeing 737");
        assertTrue(result);
        assertEquals(0, fleet.getFleet().size());
    }

    @Test
    void testRemoveAirplaneNotFound() {
        fleet.addAirplane(boeing);
        // Дія (намагаємось видалити вигаданий)
        boolean result = fleet.removeAirplane("Airbus A380");

        assertFalse(result);
        assertEquals(1, fleet.getFleet().size());
    }

    @Test
    void testFindByModel() {
        fleet.addAirplane(boeing);
        Airplane found = fleet.findByModel("Boeing 737");
        assertNotNull(found);
        assertEquals("Boeing 737", found.getModel());
    }

    @Test
    void testFindByModelNotFound() {
        Airplane found = fleet.findByModel("NonExistent");
        assertNull(found);
    }

    @Test
    void testGetTotalPassengerCapacity() {
        fleet.addAirplane(boeing);     //180 місць
        fleet.addAirplane(mriya);      //0 місць (вантажний)
        fleet.addAirplane(gulfstream); //8 місць

        double capacity = fleet.getTotalPassengerCapacity();

        //перевірка (180 + 8)
        assertEquals(188.0, capacity, 0.001);
    }

    @Test
    void testGetTotalPayloadCapacity() {
        fleet.addAirplane(boeing);     //0 тонн
        fleet.addAirplane(mriya);      //250 тонн
        fleet.addAirplane(gulfstream); //0 тонн (пасажирський)

        double capacity = fleet.getTotalPayloadCapacity();

        assertEquals(250.0, capacity, 0.001);
    }

    @Test
    void testSortByFlightRange() {
        //додаємо в неправильному порядку
        fleet.addAirplane(boeing);     // 12000
        fleet.addAirplane(mriya);      // 15000 (має бути 1-м)
        fleet.addAirplane(gulfstream); // 13000 (має бути 2-м)

        List<Airplane> sorted = fleet.sortByFlightRange();

        assertEquals(3, sorted.size());
        assertEquals("An-225 Mriya", sorted.get(0).getModel()); //15000
        assertEquals("G650", sorted.get(1).getModel()); //13000
        assertEquals("Boeing 737", sorted.get(2).getModel()); //12000
    }

    @Test
    void testFindByFuelConsumption() {
        fleet.addAirplane(boeing);     //0.8
        fleet.addAirplane(mriya);      //2.0
        fleet.addAirplane(gulfstream); //0.9

        //шукаємо між 0.7 та 1.0
        List<Airplane> result = fleet.findByFuelConsumption(0.7, 1.0);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByLuxuryLevel() {
        fleet.addAirplane(boeing);     // не джет
        fleet.addAirplane(gulfstream); // рівень 5

        List<PrivateJet> result = fleet.findByLuxuryLevel(4, 5);

        assertEquals(1, result.size());
        assertEquals("G650", result.get(0).getModel());
    }

    @Test
    void testGetFleetReturnsUnmodifiableList() {
        fleet.addAirplane(boeing);
        List<Airplane> list = fleet.getFleet();

        //намагаємось "зламати" список (додати в обхід)
        assertThrows(UnsupportedOperationException.class, () -> {
            list.add(new CargoAirplane("Test", "Test", 2000, 1, 1, 1, 1));
        });
    }

    @Test
    void testClear() {
        fleet.addAirplane(boeing);
        assertEquals(1, fleet.getFleet().size());
        fleet.clear();
        assertEquals(0, fleet.getFleet().size());
    }
}