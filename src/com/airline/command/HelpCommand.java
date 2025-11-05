package com.airline.command;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("ДОВІДКА:");
        System.out.println("Додати літак — виберіть тип і введіть параметри.");
        System.out.println("Редагувати — введіть модель, потім нові значення (Enter = залишити).");
        System.out.println("Пошук — введіть діапазон.");
        System.out.println("Файли — fleet.txt у корені проєкту.");
        System.out.println("Вихід — 12.");
    }
}