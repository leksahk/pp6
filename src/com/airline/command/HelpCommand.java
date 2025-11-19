package com.airline.command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelpCommand implements Command {
    private static final Logger logger = LogManager.getLogger(HelpCommand.class);
    @Override
    public void execute() {
        logger.info("ДОВІДКА:");
        logger.info("Додати літак — виберіть тип і введіть параметри.");
        logger.info("Редагувати — введіть модель, потім нові значення (Enter = залишити).");
        logger.info("Пошук — введіть діапазон.");
        logger.info("Файли — fleet.txt у корені проєкту.");
        logger.info("Вихід — 12.");
    }
}