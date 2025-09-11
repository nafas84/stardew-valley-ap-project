package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Interaction.Friend;
import io.Ap.StardewValley.Common.Model.Interaction.Trade;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;

public class TradeController {

    public static Result TradePrice(String username, String item, String amount, String price) {
        int id = -1;
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                id = player.getId();
                break;
            }
        }
        if (id == -1) {
            return new Result(false, "Nadarim hamchin adamio.");
        }
        boolean found = false;
        Player currentplayer = App.getGame().getCurrentPlayer();
        for (Item items : currentplayer.getInventory().getItemList()) {
            if (items.getName().equals(item)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new Result(false, "Haji nadari in itemo.");
        }
        int number;
        try {
            number = Integer.parseInt(amount);
        }
        catch (NumberFormatException error) {
            return new Result(false, "haji ye adade dorost vared kon dige.");
        }
        for (Item items : currentplayer.getInventory().getItemList()) {
            if (items.getName().equals(item)) {
                if (!currentplayer.getInventory().hasItemWithNumber(item, number)) {
                    return new Result(false, "Sharj kon haji.");
                }
            }
        }
        int priceNumber;
        try {
            priceNumber = Integer.parseInt(price);
        }
        catch (NumberFormatException error) {
            return new Result(false, "haji ye adade dorost vared kon dige.");
        }
        App.getGame().setTradeAmount(App.getGame().getTradeAmount() + 1);
        Item item1 = currentplayer.getInventory().hasItemWithName(item);
        Trade trade = new Trade("offer", currentplayer.getId(), item1, number, priceNumber,
                null, -1, App.getGame().getTradeAmount());
        currentplayer.addSentTrade(trade);
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == id) {
                player.addReceivedTrade(trade);
            }
        }
        return new Result(true, "Offer sent to " + currentplayer.getUsername() + " successfully!");
    }

    public static Result TradeItem(String username, String item, String amount, String targetItem, String targetAmount) {
        int id = -1;
        Player currentplayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                id = player.getId();
                break;
            }
        }
        if (id == -1) {
            return new Result(false, "Nadarim hamchin adamio.");
        }
        Player targetplayer = App.getGame().getPlayerByID(id);
        boolean found = false;
        for (Item items : currentplayer.getInventory().getItemList()) {
            if (items.getName().equals(item)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new Result(false, "Haji nadari in itemo.");
        }
        boolean foundTarget = false;
        for (Item items : targetplayer.getInventory().getItemList()) {
            if (items.getName().equals(targetItem)) {
                foundTarget = true;
                break;
            }
        }
        if (!foundTarget) {
            return new Result(false, "Haji nadare yaroo in itemo.");
        }
        int number;
        try {
            number = Integer.parseInt(amount);
        }
        catch (NumberFormatException error) {
            return new Result(false, "haji ye adade dorost vared kon dige.");
        }
        for (Item items : currentplayer.getInventory().getItemList()) {
            if (items.getName().equals(item)) {
                if (!currentplayer.getInventory().hasItemWithNumber(item, number)) {
                    return new Result(false, "Sharj kon haji.");
                }
            }
        }
        int numberTarget;
        try {
            numberTarget = Integer.parseInt(targetAmount);
        }
        catch (NumberFormatException error) {
            return new Result(false, "haji ye adade dorost vared kon dige.");
        }
        for (Item items : targetplayer.getInventory().getItemList()) {
            if (items.getName().equals(targetItem)) {
                if (!targetplayer.getInventory().hasItemWithNumber(targetItem, numberTarget)) {
                    return new Result(false, "Haji yaroo bayad Sharj kone.");
                }
            }
        }
        App.getGame().setTradeAmount(App.getGame().getTradeAmount() + 1);
        Item item1 = currentplayer.getInventory().hasItemWithName(item);
        Item item2 = currentplayer.getInventory().hasItemWithName(targetItem);
        Trade trade = new Trade("request", currentplayer.getId(), item1, number, -1, item2,
                numberTarget, App.getGame().getTradeAmount());
        currentplayer.addSentTrade(trade);
        targetplayer.addReceivedTrade(trade);
        return new Result(true, "Request sent to " + currentplayer.getUsername() + " successfully!");
    }

    public static Result TradeList() {
        StringBuilder builder = new StringBuilder();
        Player currentplayer = App.getGame().getCurrentPlayer();
        builder.append("ReceivedTrades:\n").append("________________________________\n");
        for (Trade trade : currentplayer.getReceivedTrades()) {
            builder.append("\t").append("tradeID: ").append(trade.getId()).append("\n");
            builder.append("\t").append("Type: ").append(trade.getType()).append("\n");
            builder.append("\t").append("Item: ").append(trade.getItem()).append("\n");
            builder.append("\t").append("Amount: ").append(trade.getAmount()).append("\n");
            if (trade.getType().equals("request")) {
                builder.append("\t").append("TargetItem: ").append(trade.getTargetItem()).append("\n");
                builder.append("\t").append("TargetAmount: ").append(trade.getTargetItemAmount()).append("\n");
            }
            else {
                builder.append("\t").append("Price: ").append(trade.getPrice()).append("\n");
            }
            builder.append("____________________________________\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result TradeResponse(String response, String ID) {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        boolean found = false;
        int tradeID;
        try {
            tradeID = Integer.parseInt(ID);
        }
        catch (NumberFormatException error) {
            return new Result(false, "Haji ye addad vared kon namoosan.");
        }
        for (Trade trade : currentPlayer.getReceivedTrades()) {
            if (trade.getId() == tradeID) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new Result(false, "Nadarim hamchin tradei.");
        }
        for (Trade trade : currentPlayer.getReceivedTrades()) {
            if (trade.getId() == tradeID) {
                if (response.equals("reject")) {
                    trade.setIsAccepted(0);
                    for (Friend friend : currentPlayer.getFriends()) {
                        if (friend.getFriendId() == trade.getSenderID()) {
                            friend.addXP(-30);
                        }
                    }
                    for (Friend friend : App.getGame().getPlayerByID(trade.getSenderID()).getFriends()) {
                        if (friend.getFriendId() == currentPlayer.getId()) {
                            friend.addXP(-30);
                        }
                    }
                    return new Result(true, "Rejected trade successfully.");
                }
                else if (response.equals("accept")) {
                    if (trade.getType().equals("offer")) {
                        if (currentPlayer.getCount() < trade.getAmount()) {
                            return new Result(false, "Pool nadari ahmaghe faghir.");
                        }
                        for (Player player : App.getGame().getPlayers()) {
                            if (player.getId() == trade.getSenderID()) {
                                if (player.getInventory().hasItemWithNumber(trade.getItem().getName(), trade.getAmount())) {
                                    return new Result(false, "Haji yaroo nadare felan boro badan bia.");
                                }
                                player.getInventory().removeItem(trade.getItem().getName(), trade.getAmount());
                                player.addCount(trade.getAmount());
                                break;
                            }
                        }
                        trade.setIsAccepted(1);
                        currentPlayer.addCount(-trade.getAmount());
                        for (Friend friend : currentPlayer.getFriends()) {
                            if (friend.getFriendId() == trade.getSenderID()) {
                                friend.addXP(50);
                            }
                        }
                        for (Friend friend : App.getGame().getPlayerByID(trade.getSenderID()).getFriends()) {
                            if (friend.getFriendId() == currentPlayer.getId()) {
                                friend.addXP(50);
                            }
                        }
                        break;
                    }
                    else {
                        if (!currentPlayer.getInventory().hasItemWithNumber(trade.getTargetItem().getName(),
                                trade.getTargetItemAmount())) {
                            return new Result(false, "Item nadari ahmaghe faghir.");
                        }
                        for (Player player : App.getGame().getPlayers()) {
                            if (player.getId() == trade.getSenderID()) {
                                if (player.getInventory().hasItemWithNumber(trade.getItem().getName(), trade.getAmount())) {
                                    return new Result(false, "Haji yaroo nadare felan boro badan bia.");
                                }
                                player.getInventory().removeItem(trade.getItem().getName(), trade.getAmount());
                                player.getInventory().addItem(trade.getTargetItem(), trade.getTargetItemAmount());
                                break;
                            }
                        }
                        trade.setIsAccepted(1);
                        currentPlayer.getInventory().addItem(trade.getItem(), trade.getAmount());
                        for (Friend friend : currentPlayer.getFriends()) {
                            if (friend.getFriendId() == trade.getSenderID()) {
                                friend.addXP(50);
                            }
                        }
                        for (Friend friend : App.getGame().getPlayerByID(trade.getSenderID()).getFriends()) {
                            if (friend.getFriendId() == currentPlayer.getId()) {
                                friend.addXP(50);
                            }
                        }
                        break;
                    }
                }
            }
        }

        return new Result(true, "Accepted trade successfully.");
    }

    public static Result TradeHistory() {
        StringBuilder builder = new StringBuilder();
        Player currentplayer = App.getGame().getCurrentPlayer();
        builder.append("TradeHistory:\n\n");
        builder.append("ReceivedTrades:\n").append("________________________________\n");
        for (Trade trade : currentplayer.getReceivedTrades()) {
            builder.append("\t").append("tradeID: ").append(trade.getId()).append("\n");
            builder.append("\t").append("State: ");
            if (trade.getIsAccepted() == -1) {
                builder.append("undetermined\n");
            }
            else if (trade.getIsAccepted() == 1) {
                builder.append("accepted\n");
            }
            else {
                builder.append("rejected\n");
            }
            builder.append("\t").append("Type: ").append(trade.getType()).append("\n");
            builder.append("\t").append("Item: ").append(trade.getItem()).append("\n");
            builder.append("\t").append("Amount: ").append(trade.getAmount()).append("\n");
            if (trade.getType().equals("request")) {
                builder.append("\t").append("TargetItem: ").append(trade.getTargetItem()).append("\n");
                builder.append("\t").append("TargetAmount: ").append(trade.getTargetItemAmount()).append("\n");
            }
            else {
                builder.append("\t").append("Price: ").append(trade.getPrice()).append("\n");
            }
            builder.append("____________________________________\n");
        }
        builder.append("\n\tSent Trades:\n\n").append("________________________________\n");
        for (Trade trade : currentplayer.getSentTrades()) {
            builder.append("\t").append("tradeID: ").append(trade.getId()).append("\n");
            builder.append("\t").append("State: ");
            if (trade.getIsAccepted() == -1) {
                builder.append("undetermined\n");
            }
            else if (trade.getIsAccepted() == 1) {
                builder.append("accepted\n");
            }
            else {
                builder.append("rejected\n");
            }
            builder.append("\t").append("Type: ").append(trade.getType()).append("\n");
            builder.append("\t").append("Item: ").append(trade.getItem()).append("\n");
            builder.append("\t").append("Amount: ").append(trade.getAmount()).append("\n");
            if (trade.getType().equals("request")) {
                builder.append("\t").append("TargetItem: ").append(trade.getTargetItem()).append("\n");
                builder.append("\t").append("TargetAmount: ").append(trade.getTargetItemAmount()).append("\n");
            }
            else {
                builder.append("\t").append("Price: ").append(trade.getPrice()).append("\n");
            }
            builder.append("____________________________________\n");
        }
        return new Result(true, builder.toString());
    }
}
