package com.airline.service;

import com.airline.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void serialize_PassengerAirplane() {
        Airplane plane = new PassengerAirplane("Boeing 737", "Boeing", 2020, 900, 12000, 0.8, 180);
        String expected = "PassengerAirplane;Boeing 737;Boeing;2020;900.00;12000.00;0.80;180";
        String actual = FileHandler.serialize(plane);
        assertEquals(expected, actual.replace(',', '.'));
    }

    @Test
    void serialize_PrivateJet() {
        Airplane plane = new PrivateJet("G650", "Gulfstream", 2018, 1100, 13000, 0.9, 8, 5);
        String expected = "PrivateJet;G650;Gulfstream;2018;1100.00;13000.00;0.90;8;5";
        String actual = FileHandler.serialize(plane);
        assertEquals(expected, actual.replace(',', '.'));
    }

    @Test
    void deserialize_CargoAirplane() {
        String line = "CargoAirplane;An-225 Mriya;Antonov;1988;850.00;15000.00;2.00;250.0";
        Airplane plane = FileHandler.deserialize(line);

        assertNotNull(plane);
        assertTrue(plane instanceof CargoAirplane);
        assertEquals("An-225 Mriya", plane.getModel());
        assertEquals(1988, plane.getYearOfManufacture());
        assertEquals(2.0, plane.getFuelConsumption());

        CargoAirplane cargo = (CargoAirplane) plane;
        assertEquals(250.0, cargo.getPayloadCapacity());
    }

    @Test
    void deserialize_PassengerAirplane_WithComma() {
        String line = "PassengerAirplane;Boeing 737;Boeing;2020;900,00;12000,00;0,80;180";
        Airplane plane = FileHandler.deserialize(line);

        assertNotNull(plane);
        assertTrue(plane instanceof PassengerAirplane);
        assertEquals(900.0, plane.getMaxSpeed());
        assertEquals(0.8, plane.getFuelConsumption());
    }

    @Test
    void deserialize_ThrowsException_IfLineTooShort() {
        String line = "PassengerAirplane;Boeing 737;Boeing";
        assertThrows(IllegalArgumentException.class, () -> {
            FileHandler.deserialize(line);
        });
    }

    @Test
    void deserialize_ThrowsException_IfTypeUnknown() {
        String line = "UnknownType;X;Y;2022;1000.00;10000.00;1.00;100";
        assertThrows(IllegalArgumentException.class, () -> {
            FileHandler.deserialize(line);
        });
    }

}
