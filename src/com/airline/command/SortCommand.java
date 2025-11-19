package com.airline.command;
import com.airline.service.AirlineFleet;
import com.airline.model.Airplane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SortCommand.class);
    private final AirlineFleet fleet;

    public SortCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        var sorted = fleet.sortByFlightRange();
        logger.info("\nСортування за дальністю польоту:");
        if (sorted.isEmpty()) {
            logger.warn("Немає літаків.");
        } else {
            logger.info("Результат сортування:");
            for (Airplane p : sorted) {
                logger.info("{}", p);
            }
        }
    }
}