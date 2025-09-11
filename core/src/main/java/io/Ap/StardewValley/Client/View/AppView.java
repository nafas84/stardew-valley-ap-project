package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Command.Menu;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    Scanner scanner = new Scanner(System.in);

    public void run() throws IOException {
        App.loadApp();
        while (App.getCurrentMenu() != Menu.ExitMenu) {
            App.getCurrentMenu().checkCommand(scanner);
        }
    }
}
