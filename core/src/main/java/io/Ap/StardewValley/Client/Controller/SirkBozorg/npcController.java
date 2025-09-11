package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.NPC.NPC;
import io.Ap.StardewValley.Common.Model.NPC.Quest;
import io.Ap.StardewValley.Common.Model.Player.AiChat;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Season;

public class npcController {

    public static Result meetNPC(String npcName, String message) {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        boolean found = false;
        NPC target = null;
        for (NPC npc : App.getGame().getNPCs()) {
            if (npc.getName().equals(npcName)) {
                found = true;
                target = npc;
                break;
            }
        }
        if (!found) {
            return new Result(false, "NPC nadarim.");
        }
//        if ((Math.abs(target.getCoordinate().getX() - currentPlayer.getCoordinate().getX()) > 1)
//                || (Math.abs(target.getCoordinate().getY() - currentPlayer.getCoordinate().getY()) > 1)) {
//            return new Result(false, "NPC doore chaghi.");
//        }
        String response = AiChat.getNpcDialogue(message , target.getContext(App.getGame()));
        return new Result(true, response);
    }

    public static Result giftNPC(String npcName, String item) {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        boolean found = false;
        NPC target = null;
        for (NPC npc : App.getGame().getNPCs()) {
            if (npc.getName().equals(npcName)) {
                found = true;
                target = npc;
                break;
            }
        }
        if (!found) {
            return new Result(false, "nadarim hamchin khario.");
        }
        if ((Math.abs(target.getCoordinate().getX() - currentPlayer.getCoordinate().getX()) > 1)
                || (Math.abs(target.getCoordinate().getY() - currentPlayer.getCoordinate().getY()) > 1)) {
            return new Result(false, "NPC doore chaghi.");
        }
        if (!currentPlayer.getInventory().hasItemWithNumber(item, 1)) {
            return new Result(false, "chaghi nadari in itemo.");
        }
        Item sentItem = currentPlayer.getInventory().hasItemWithName(item);
        currentPlayer.removeItemFromInventory(item, 1);
        target.addReceivedItems(sentItem);
        return new Result(true, "delivered gift successfully.");
    }

    public static Result friendshipNPCList() {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        StringBuilder builder = new StringBuilder();
        builder.append("NPC list:\n\n");
        int counter = 0;
        for (NPC npc : App.getGame().getNPCs()) {
            counter++;
            builder.append("\t").append(counter).append("- ").append(npc.getName()).append(":\n");
            builder.append("\t\t").append("Xp: ").append(npc.getFriendXp(currentPlayer)).append("\n");
            builder.append("\t\t").append("Level: ").append(npc.getFriendXp(currentPlayer) / 200).append("\n\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result questList() {
        Player currentPlayer = App.getGame().getCurrentPlayer();
        StringBuilder builder = new StringBuilder();
        builder.append("Quest list:\n\n");
        int counter = 0;
        for (NPC npc : App.getGame().getNPCs()) {
            for (Quest quest : npc.getQuests()) {
                if (quest.getQuestLevel() == 1) {
                    counter++;
                    builder.append(quest.toString(counter)).append("\n");
                }
                if (quest.getQuestLevel() == 2) {
                    if (npc.getLevel(currentPlayer) > 0) {
                        counter++;
                        builder.append(quest.toString(counter)).append("\n");
                    }
                }
                if (quest.getQuestLevel() == 3) {
                    if (npc.getName().equals("Ali") || npc.getName().equals("Parastoo")) {
                        if (App.getGame().getCurrentTime().getSeason() == Season.Spring) {
                            counter++;
                            builder.append(quest.toString(counter)).append("\n");
                        }
                    }
                    if (npc.getName().equals("Negar")) {
                        if (App.getGame().getCurrentTime().getSeason() == Season.Summer) {
                            counter++;
                            builder.append(quest.toString(counter)).append("\n");
                        }
                    }
                    if (npc.getName().equals("Farshad")) {
                        if (App.getGame().getCurrentTime().getSeason() == Season.Fall) {
                            counter++;
                            builder.append(quest.toString(counter)).append("\n");
                        }
                    }
                    if (npc.getName().equals("Milad")) {
                        if (App.getGame().getCurrentTime().getSeason() == Season.Winter) {
                            counter++;
                            builder.append(quest.toString(counter)).append("\n");
                        }
                    }
                }
            }
        }
        return new Result(true, builder.toString());
    }
}
