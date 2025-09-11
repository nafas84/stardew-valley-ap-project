package io.Ap.StardewValley.Client.Controller;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Command.Menu;
import io.Ap.StardewValley.Common.Model.Result;

import java.io.FileWriter;
import java.io.IOException;

public class MainMenuController {
    public static Result logout() {
        handleStayLogin();
        App.setCurrentMenu(Menu.LoginMenu);

        return new Result(true, "You have successfully logged out. Now you are in login menu");
    }

    public static Result goMenu (String menu) {
        if (!menu.equals("game") && !menu.equals("profile")) {
            return new Result(false, "You can't go to " + menu + " menu in main menu with go to command");
        }

        if (menu.equals("game")) App.setCurrentMenu(Menu.GameMenu);
        else App.setCurrentMenu(Menu.ProfileMenu);
        return new Result(true, "Now you are in " + menu + " menu");
    }

    public static Result currentMenu () {
        return new Result(true, "You are in main menu");
    }

    public static Result exitMenu () {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "Now you are in login menu");
    }


    //added by aynaz:
    public static void handleStayLogin() {
        App.setCurrentUser(null);
        try (FileWriter writer = new FileWriter("data/users/loggedIn.json")) {
            writer.write("null");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
