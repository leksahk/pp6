package com.airline;

import com.airline.command.*;
import com.airline.service.AirlineFleet;
import com.airline.service.FileHandler;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AirlineFleet fleet = new AirlineFleet(50);
        FileHandler.load(fleet);
        Scanner scanner = new Scanner(System.in);

        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, new AddCommand(fleet, scanner));
        commands.put(2, new RemoveCommand(fleet, scanner));
        commands.put(3, new EditCommand(fleet, scanner));
        commands.put(4, new DisplayCommand(fleet));
        commands.put(5, new SortCommand(fleet));
        commands.put(6, new SearchFuelCommand(fleet, scanner));
        commands.put(7, new SearchLuxuryCommand(fleet, scanner));
        commands.put(8, new ReportCommand(fleet));
        commands.put(9, new LoadCommand(fleet));
        commands.put(10, new SaveCommand(fleet));
        commands.put(11, new HelpCommand());
        commands.put(12, new ExitCommand());

        while (true) {
            System.out.println("\nМЕНЕДЖЕР ФЛОТУ АВІАКОМПАНІЇ");
            System.out.println("1. Додати літак");
            System.out.println("2. Видалити літак");
            System.out.println("3. Редагувати літак");
            System.out.println("4. Показати флот");
            System.out.println("5. Сортувати за дальністю");
            System.out.println("6. Пошук за витратами пального");
            System.out.println("7. Пошук джетів за розкішшю");
            System.out.println("8. Звіт");
            System.out.println("9. Завантажити з файлу");
            System.out.println("10. Зберегти у файл");
            System.out.println("11. Довідка");
            System.out.println("12. Вихід");
            System.out.print("Виберіть: ");

            int choice = scanner.nextInt(); scanner.nextLine();
            Command cmd = commands.get(choice);
            if (cmd != null) {
                cmd.execute();
            } else {
                System.out.println("Неправильний вибір. Спробуйте ще раз.");
            }
        }
    }
}