package com.airline.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrivateJetTest {
    private final PrivateJet jet = new PrivateJet("G650", "Gulfstream", 2018, 1100, 13000, 0.9, 8, 5);

    @Test
    void setPassengersTest() {
        //менше 1
        assertThrows(IllegalArgumentException.class, () -> {
            jet.setPassengers(0);
        });
        //більше 10
        assertThrows(IllegalArgumentException.class, () -> {
            jet.setPassengers(11);
        });
    }

    @Test
    void setLuxuryLevelTest() {
        //менше 1
        assertThrows(IllegalArgumentException.class, () -> {
            jet.setLuxuryLevel(0);
        });
        //більше 5
        assertThrows(IllegalArgumentException.class, () -> {
            jet.setLuxuryLevel(6);
        });
    }

    @Test
    void CalculateCapacityTest() {
        //перевіряємо, що 'calculateCapacity' повертає кількість пасажирів
        assertEquals(8.0, jet.calculateCapacity(), 0.001);
    }

    @Test
    void constructorTest() {
        //1800 рік має кинути виняток
        assertThrows(IllegalArgumentException.class, () -> {
            new PrivateJet("G650", "Gulfstream", 1800, 1100, 13000, 0.9, 8, 5);
        });
    }
}