package com.airline.command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Дякую за використання!");
        System.exit(0);
    }
}