package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Interaction.Friend;
import io.Ap.StardewValley.Common.Model.Interaction.Gift;
import io.Ap.StardewValley.Common.Model.Interaction.Talk;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Player.GiftItem;
import io.Ap.StardewValley.Common.Model.Player.GiftType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;

public class InteractionController {

    public static Result showFriendships () {
        StringBuilder builder = new StringBuilder();
        builder.append("Friendships:\n").append("________________________________\n");
        for (Friend friend : App.getGame().getCurrentPlayer().getFriends()) {
            builder.append(friend.getFriendName()).append(":\n");
            builder.append("\t").append("Friendship Level: ").append(friend.getLevel()).append("\n");
            builder.append("\t").append("Friendship xp: ").append(friend.getXp()).append("\n");
            builder.append("________________________________\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result talk (String username, String message) {
        int friendId = -1;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendId = player.getId();
                Coordinate c = new Coordinate(player.getCoordinate().getX(), player.getCoordinate().getY());
                if (Math.abs(c.getX() - currentPlayer.getCoordinate().getX()) > 1
                        || Math.abs(c.getY() - currentPlayer.getCoordinate().getY()) > 1) {
                    return new Result(false, "Haji yaroo kheili doore namoosan.");
                }
                break;
            }
        }
        if (friendId == -1) {
            return new Result(false, "Haji in kie dige?");
        }
        Talk talk = new Talk(currentPlayer.getId(), friendId, message);
        App.getGame().addTalk(talk);
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendId) {
                if (!friend.isTalkedToday()) {
                    friend.setTalkedToday(true);
                    friend.addXP(20);
                    friend.updateLevel();
                }
            }
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendId) {
                player.addNotification("You have recived a message from " + currentPlayer.getUsername() + "!");
                for (Friend friend : currentPlayer.getFriends()) {
                    if (friend.getFriendId() == currentPlayer.getId()) {
                        if (!friend.isTalkedToday()) {
                            friend.setTalkedToday(true);
                            friend.addXP(20);
                            friend.updateLevel();
                        }
                    }
                }
            }
        }
        return new Result(true, "delivered message successfully.");
    }

    public static Result showTalkHistory (String username) {
        int friendId = -1;
        Player friendPlayer = null;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendId = player.getId();
                friendPlayer = player;
                break;
            }
        }
        if (friendId == -1) {
            return new Result(false, "Haji tavahom zadi? nadarim hamchin kesi.");
        }
        boolean isTalkedEver = false;
        for (Talk talk : App.getGame().getTalks()) {
            if ((talk.getSenderId() == currentPlayer.getId() && talk.getReceiverId() == friendId)
                    || (talk.getSenderId() == friendId && talk.getReceiverId() == currentPlayer.getId())) {
                isTalkedEver = true;
            }
        }
        if (!isTalkedEver) {
            return new Result(false, "Ta hala chizi nagoftid.");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Message history:\n").append("________________________________\n");
        for (Talk talk : App.getGame().getTalks()) {
            if (talk.getSenderId() == currentPlayer.getId() && talk.getReceiverId() == friendId) {
                builder.append("\tYou: ").append(talk.getMessage()).append("\n");
            }
            if (talk.getSenderId() == friendId && talk.getReceiverId() == currentPlayer.getId()) {
                builder.append("\t").append(friendPlayer.getUsername()).append(": ").append(talk.getMessage()).append("\n");
            }
        }
        return new Result(true, builder.toString());
    }

    public static Result gift (String username, String item, String amount) {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        int friendId = -1;
        int quantity;
        try {
            quantity = Integer.parseInt(amount);
        }
        catch (NumberFormatException error) {
            return new Result(false, "Ye adad vared kon.");
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendId = player.getId();
                if (Math.abs (player.getCoordinate().getX() - currentPlayer.getCoordinate().getX()) > 1
                        || Math.abs (player.getCoordinate().getY() - currentPlayer.getCoordinate().getY()) > 1) {
                    return new Result(false, "Haji kheili doore namoosan.");
                }
                break;
            }
        }
        if (friendId == -1) {
            return new Result(false, "Haji nist hmchin adami.");
        }
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendId) {
                if (friend.getLevel() < 1)
                    return new Result(false, "Hanooz kheili refigh nistid ba ham.");
            }
        }
        boolean result = currentPlayer.getInventory().hasItemWithNumber(item, quantity);
        if (!result) {
            return new Result(false, "Hamchin itemi nadari be andaze kafi.");
        }
        currentPlayer.setNumberOfGiftsSent(currentPlayer.getNumberOfGiftsSent() + 1);
        Gift gift = new Gift(currentPlayer.getAndRemoveItemsFromInventory(quantity, item),
                currentPlayer.getUsername(), friendId, currentPlayer.getNumberOfGiftsSent());
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendId) {
                if (!friend.isGiftedToday()) {
                    friend.setGiftedToday(true);
                    friend.addXP(50);
                    friend.updateLevel();
                }
            }
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendId) {
                player.addNotification("You have recived a gift from " + currentPlayer.getUsername() + "!");
                player.addGiftToGifts(gift);
                player.addGiftToInventory(gift);
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == currentPlayer.getId()) {
                        if (!friend.isGiftedToday()) {
                            friend.setGiftedToday(true);
                            friend.addXP(50);
                            friend.updateLevel();
                        }
                    }
                }
            }
        }
        return new Result(true, "delivered gift successfully.");
    }

    public static Result giftList () {
        StringBuilder builder = new StringBuilder();
        if (App.getGame().getCurrentPlayer().getGifts().isEmpty()) {
            return new Result(true, "You've not received any gift");
        }
        builder.append("Received gifts:\n").append("________________________________\n");
        for (Gift gift : App.getGame().getCurrentPlayer().getGifts()) {
            builder.append("\tGiftName: ").append(gift.getGift().get(0).getName()).append("\n");
            builder.append("\tSender: ").append(gift.getSender()).append("\n");
            builder.append("\tisAccepted: ");
            if (gift.getIsAccepted() == -1)
                builder.append("Not accepted Yet\n");
            else if (gift.getIsAccepted() == 0)
                builder.append("Rejected\n");
            else
                builder.append("Accepted\n");
            builder.append("\tRate: ");
            if (gift.getRate() == -1)
                builder.append("Not rated Yet\n");
            else
                builder.append(gift.getRate()).append("\n");
            builder.append("________________________________\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result rateGift (String giftNumber, String rate) {
        int id;
        try {
            id = Integer.parseInt(giftNumber);
        }
        catch (NumberFormatException error) {
            return new Result(false, "Shomare bede ablah.");
        }
        int rateNumber;
        try {
            rateNumber = Integer.parseInt(rate);
        }
        catch (NumberFormatException error) {
            return new Result(false, "Addad sahih bede chaghal.");
        }
        Player currentPlayer = App.getGame().getCurrentPlayer();
        int friendId = -1;
        for (Gift gift : currentPlayer.getGifts()) {
            if (gift.getGiftID() == id) {
                if (gift.getRate() != -1) {
                    return new Result(false, "Ghablan rate dadi chaghal.");
                }
                for (Player player : App.getGame().getPlayers()) {
                    if (player.getUsername().equals(gift.getSender())) {
                        friendId = player.getId();
                        break;
                    }
                }
                gift.setRate(rateNumber);
            }
        }
        if (friendId == -1) {
            return new Result(false, "Hamchin gifti nadarim.");
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == currentPlayer.getId()) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == friendId) {
                        friend.rateGift(rateNumber);
                        friend.updateLevel();
                    }
                }
            }
            if (player.getId() == friendId) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == currentPlayer.getId()) {
                        friend.rateGift(rateNumber);
                        friend.updateLevel();
                    }
                }
            }
        }
        return new Result(true, "gift rated successfully.");
    }

    public static Result giftHistory (String username) {
        int friendID = -1;
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendID = player.getId();
                break;
            }
        }
        if (friendID == -1) {
            return new Result(false, "Hamchin adami nadarim.");
        }
        Player currentPlayer = App.getGame().getCurrentPlayer();
        StringBuilder builder = new StringBuilder();
        builder.append("Sent gifts history:\n").append("________________________________\n");
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendID) {
                for (Gift gift : friend.getSentGifts()) {
                    builder.append("\tGiftName: ").append(gift.getGift().get(0).getName()).append("\n");
                    builder.append("\tCount: ").append(gift.getCount()).append("\n");
                    builder.append("________________________________\n");
                }
            }
        }
        builder.append("\n");
        builder.append("Received gifts:\n").append("________________________________\n");
        for (Friend friend : App.getGame().getPlayerByID(friendID).getFriends()) {
            if (friend.getFriendId() == currentPlayer.getId()) {
                for (Gift gift : friend.getSentGifts()) {
                    builder.append("\tGiftName: ").append(gift.getGift().get(0).getName()).append("\n");
                    builder.append("\tCount: ").append(gift.getCount()).append("\n");
                }
            }
        }
        return new Result(true, builder.toString());
    }

    public static Result hug (String username) {
        int friendID = -1;
        int level = -1;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendID = player.getId();
                break;
            }
        }
        if (friendID == -1) {
            return new Result(false, "Too tanhaeet bemir.");
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                if (Math.abs(currentPlayer.getCoordinate().getX() - player.getCoordinate().getX()) > 1 ||
                        Math.abs(currentPlayer.getCoordinate().getY() - player.getCoordinate().getY()) > 1) {
                    return new Result(false, "Kheili doorid az ham.");
                }
            }
        }
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendID) {
                if (friend.getLevel() < 2) {
                    return new Result(false, "Hanoo refigh nistid oonghadr.");
                }
            }
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == currentPlayer.getId()) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == friendID) {
                        if (!friend.isHuggedToday()) {
                            friend.setHuggedToday(true);
                            friend.addXP(60);
                            friend.updateLevel();
                        }
                    }
                }
            }
            if (player.getId() == friendID) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == currentPlayer.getId()) {
                        if (!friend.isHuggedToday()) {
                            friend.setHuggedToday(true);
                            friend.addXP(60);
                            friend.updateLevel();
                        }
                    }
                }
            }
        }
        return new Result(true, "Hugged successfully.");
    }

    public static Result giveFlower (String username) {
        int friendID = -1;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendID = player.getId();
                break;
            }
        }
        if (friendID == -1) {
            return new Result(false, "Nadraim agha.");
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                if (Math.abs(currentPlayer.getCoordinate().getX() - player.getCoordinate().getX()) > 1 ||
                        Math.abs(currentPlayer.getCoordinate().getY() - player.getCoordinate().getY()) > 1) {
                    return new Result(false, "Boro Nazdiktar.");
                }
            }
        }
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendID) {
                if (friend.getLevel() < 2 || friend.getXp() < 300) {
                    return new Result(false, "Bayad doostTar bashid.");
                }
            }
        }
        boolean found = false;
        for (Item item : currentPlayer.getInventory().getItemList()) {
            if (item.getName().equals("Bouquet")) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new Result(false, "Boro Gol bekhar geda goshne.");
        }
        currentPlayer.removeItemFromInventory("Bouquet" , 1);
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                player.addItemToInventory(new GiftItem(GiftType.Bouquet), 1);
            }
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == currentPlayer.getId()) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == friendID) {
                        friend.nextLevel();
                    }
                }
            }
            if (player.getId() == friendID) {
                for (Friend friend : player.getFriends()) {
                    if (friend.getFriendId() == currentPlayer.getId()) {
                        friend.nextLevel();
                    }
                }
            }
        }
        return new Result(true, "Gaved bouquet to " + App.getGame().getPlayerByID(friendID).getUsername());
    }

    public static Result askMarriage (String username) {
        int friendID = -1;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                friendID = player.getId();
                break;
            }
        }
        if (friendID == -1) {
            return new Result(false, "Boro khoda roozito jaye dige bede.");
        }
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                if (Math.abs(currentPlayer.getCoordinate().getX() - player.getCoordinate().getX()) > 1 ||
                        Math.abs(currentPlayer.getCoordinate().getY() - player.getCoordinate().getY()) > 1) {
                    return new Result(false, "Doori o doosti?");
                }
            }
        }
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendID) {
                if (friend.getLevel() < 3 || friend.getXp() < 400) {
                    return new Result(false, "Bayad bishtar mokhesho bezani.");
                }
            }
        }
        boolean found = false;
        for (Item item : currentPlayer.getInventory().getItemList()) {
            if (item.getName().equals("Wedding Ring")) {
                found = true;
                break;
            }
        }
        if (!found)
            return new Result(false, "Boro ring bekhar esfahani ahmagh.");
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                player.addItemToInventory(new GiftItem(GiftType.WeddingRing), 1);
                player.addNotification("You've received a marriage request from " + currentPlayer.getUsername() + "!");
                break;
            }
        }
        return new Result(true, "Marriage request sent to " + App.getGame().getPlayerByID(friendID).
                getUsername() + " successfully. mobarake agha, ma ke be yaar naresidim :(");
    }

    public static Result respondMarriage (String answer, String username) {
        int friendID = -1;
        Player currentPlayer = App.getGame().getCurrentPlayer();
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == friendID) {
                friendID = player.getId();
                break;
            }
        }
        if (friendID == -1) {
            return new Result(false, "Tavaho nazan agha nadarim hamchin adami.");
        }
        Player partner = App.getGame().getPlayerByID(friendID);
        if (answer.equals("accept")) {
            currentPlayer.setPartnerID(friendID);
            partner.setPartnerID(currentPlayer.getId());
            partner.removeItemFromInventory("Wedding Ring" , 1);
            for (Friend friend : currentPlayer.getFriends()) {
                if (friend.getFriendId() == friendID) {
                    friend.setLevel(4);
                }
            }
            for (Friend friend : partner.getFriends()) {
                if (friend.getFriendId() == currentPlayer.getId()) {
                    friend.setLevel(4);
                }
            }
            return new Result(true, "Ishala be paye ham pir shid. ma ke dar tanhaei be goor miravim :)");
        }
        for (Friend friend : currentPlayer.getFriends()) {
            if (friend.getFriendId() == friendID) {
                friend.setLevel(0);
            }
        }
        for (Friend friend : partner.getFriends()) {
            if (friend.getFriendId() == currentPlayer.getId()) {
                friend.setLevel(0);
            }
        }
        return new Result(true, "Boro to tanhaeit bemir ahmaghe javgir. az in behtar giret nemiad!");
    }
}
