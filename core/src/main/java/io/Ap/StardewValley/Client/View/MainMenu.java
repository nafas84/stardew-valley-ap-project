package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Client.Controller.MainMenuController;
import io.Ap.StardewValley.Common.Model.Command.MainMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if (MainMenuCommand.Logout.getMatcher(input) != null) {
            System.out.println(MainMenuController.logout());
        } else if ((matcher = MainMenuCommand.GoMenu.getMatcher(input)) != null) {
            System.out.println(MainMenuController.goMenu(matcher.group("menu")));
        } else if (MainMenuCommand.CurrentMenu.getMatcher(input) != null) {
            System.out.println(MainMenuController.currentMenu());
        } else if (MainMenuCommand.Exit.getMatcher(input) != null) {
            System.out.println(MainMenuController.exitMenu());
        } else {
            System.out.println("invalid command");
        }
    }
}
