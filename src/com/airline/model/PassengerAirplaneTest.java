package com.airline.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerAirplaneTest {

    @Test
    void constructorTest() {
        //перевірка нижньої межі (1903)
        assertThrows(IllegalArgumentException.class, () -> {
            new PassengerAirplane("737", "Boeing", 1900, 800, 1000, 1, 150);
        });

        //перевірка верхньої межі (майбутнє)
        int futureYear = java.time.Year.now().getValue() + 1;
        assertThrows(IllegalArgumentException.class, () -> {
            new PassengerAirplane("737", "Boeing", futureYear, 800, 1000, 1, 150);
        });
    }

    @Test
    void setMaxSpeedTest() {
        PassengerAirplane plane = new PassengerAirplane("737", "Boeing", 2020, 800, 1000, 1, 150);
        //перевірка 0
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setMaxSpeed(0);
        });
        //перевірка негативного
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setMaxSpeed(-100);
        });
    }

    @Test
    void setPassengerSeatsTest() {
        PassengerAirplane plane = new PassengerAirplane("737", "Boeing", 2020, 800, 1000, 1, 150);

        //перевірка 0
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setPassengerSeats(0);
        });

        //перевірка > 1000 (верхня межа)
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setPassengerSeats(1001);
        });
    }

    @Test
    void CalculateCapacityTest() {
        PassengerAirplane plane = new PassengerAirplane("737", "Boeing", 2020, 800, 1000, 1, 150);
        assertEquals(150.0, plane.calculateCapacity());
    }
}