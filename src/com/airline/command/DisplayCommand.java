package com.airline.command;
import com.airline.model.Airplane;
import com.airline.service.AirlineFleet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DisplayCommand.class);
    private final AirlineFleet fleet;

    public DisplayCommand(AirlineFleet fleet) {
        this.fleet = fleet;
    }

    @Override
    public void execute() {
        logger.info("\nФлот авіакомпанії:");
        if (fleet.getFleet().isEmpty()) {
            logger.warn("Флот порожній.");
        } else {
            for (Airplane p : fleet.getFleet()) {
                logger.info("{}", p);
            }
        }
    }
}