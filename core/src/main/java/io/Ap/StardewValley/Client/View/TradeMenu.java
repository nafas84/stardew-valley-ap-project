package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.TradeController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Command.Menu;
import io.Ap.StardewValley.Common.Model.Command.TradeMenuCommand;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = TradeMenuCommand.TradePrice.getMatcher(input)) != null) {
            System.out.println(TradeController.TradePrice(matcher.group("username"), matcher.group("item"),
                    matcher.group("amount"), matcher.group("price")));
        }
        else if ((matcher = TradeMenuCommand.TradeItem.getMatcher(input)) != null) {
            System.out.println(TradeController.TradeItem(matcher.group("username"), matcher.group("item"),
                    matcher.group("amount"), matcher.group("targetItem"), matcher.group("targetAmount")));
        }
        else if (TradeMenuCommand.TradeLIst.getMatcher(input) != null) {
            System.out.println(TradeController.TradeList());
        }
        else if ((matcher = TradeMenuCommand.TradeRespond.getMatcher(input)) != null) {
            System.out.println(TradeController.TradeResponse(matcher.group("response"), matcher.group("id")));
        }
        else if ((matcher = TradeMenuCommand.TradeHistory.getMatcher(input)) != null) {
            System.out.println(TradeController.TradeHistory());
        }
        else if ((matcher = TradeMenuCommand.BackToGameMenu.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.GameMenu);
        }
        else {
            System.out.println("invalid command");
        }
    }
}
