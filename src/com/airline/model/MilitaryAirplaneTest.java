package com.airline.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MilitaryAirplaneTest {
    private final MilitaryAirplane plane = new MilitaryAirplane("F-35", "Lockheed", 2015, 1900, 2200, 2.1, 2.5);

    @Test
    void setWeaponLoadTest() {
        //load >= 0
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setWeaponLoad(-0.01);
        });
    }

    @Test
    void setWeaponLoad_ShouldAllowZero() {
        //перевіряємо чи 0.0 дозволено
        assertDoesNotThrow(() -> {
            plane.setWeaponLoad(0.0);
        });
        assertEquals(0.0, plane.getWeaponLoad(), 0.001);
    }

    @Test
    void testCalculateCapacity() {
        //'calculateCapacity' повертає вантаж озброєння
        assertEquals(2.5, plane.calculateCapacity(), 0.001);
    }

    @Test
    void constructorTest() {
        //перевіряємо успадковану логіку: валідація виробника (не може бути порожнім)
        assertThrows(IllegalArgumentException.class, () -> {
            new MilitaryAirplane("F-35", "", 2015, 1900, 2200, 2.1, 2.5);
        });
    }

    @Test
    void setMaxSpeedTest() {
        //швидкість не може бути 0
        assertThrows(IllegalArgumentException.class, () -> {
            plane.setMaxSpeed(0);
        });
    }
}