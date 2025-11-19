package com.airline.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CargoAirplaneTest {
    private final CargoAirplane plane = new CargoAirplane("An-124 Ruslan", "Antonov", 1980, 800, 7000, 1.5, 150);

    @Test
    void setPayloadCapacityTest() {
        //перевірка, що 'setPayloadCapacity' кидає виняток для 0
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setPayloadCapacity(0);
        });

        //перевірка негативного значення
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setPayloadCapacity(-50.5);
        });
    }

    @Test
    void testCalculateCapacity() {
        //перевіряємо, що 'calculateCapacity' повертає саме вантаж
        assertEquals(150.0, plane.calculateCapacity(), 0.001);
    }

    @Test
    void constructorTest() {
        //1800 рік має кинути виняток
        assertThrows(IllegalArgumentException.class, () -> {
            new CargoAirplane("An-124", "Antonov", 1800, 800, 7000, 1.5, 150);
        });
    }
}