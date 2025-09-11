package io.Ap.StardewValley.Common.Model.Command;

import io.Ap.StardewValley.Client.View.*;

import java.io.IOException;
import java.util.Scanner;

public enum Menu {
    MainMenu (new MainMenu()),
    LoginMenu (new LoginMenu()),
    GameMenu (new GameMenu()),
    ProfileMenu (new ProfileMenu()),
    TradeMenu (new TradeMenu()),
    ExitMenu (new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand (Scanner scanner) throws IOException {
        this.menu.check(scanner);
    }
}
