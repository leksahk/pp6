package com.airline.command;
import com.airline.service.AirlineFleet;
import com.airline.model.Airplane;

public class SortCommand implements Command {
    private final AirlineFleet fleet;

    public SortCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        var sorted = fleet.sortByFlightRange();
        System.out.println("\nСортування за дальністю польоту:");
        if (sorted.isEmpty()) {
            System.out.println("Немає літаків.");
        } else {
            for (Airplane p : sorted) {
                System.out.println(p);
            }
        }
    }
}