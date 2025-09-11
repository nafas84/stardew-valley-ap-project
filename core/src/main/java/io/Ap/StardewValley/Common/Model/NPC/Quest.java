package io.Ap.StardewValley.Common.Model.NPC;

import io.Ap.StardewValley.Common.Model.Item.Item;

public class Quest {
    private Item questItem;
    private Item rewardItem;
    private int itemCount;
    private int rewardCount;
    private int questLevel;
    private int questState;
    private int questID;

    public Quest(Item questItem, int itemCount, Item rewardItem, int rewardCount, int questLevel, int questID) {
        this.questItem = questItem;
        this.rewardItem = rewardItem;
        this.itemCount = itemCount;
        this.rewardCount = rewardCount;
        this.questLevel = questLevel;
        this.questState = 0;
        this.questID = questID;
    }

    public Item getQuestItem() {
        return this.questItem;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public int getQuestLevel() {
        return this.questLevel;
    }

    public int getQuestState() {
        return this.questState;
    }

    public void setQuestState(int questState) {
        this.questState = questState;
    }

    public String toString(int counter) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t").append(counter).append("- ").append("Quest ID: ").append(this.questID).append("\n");
        if (this.questState == 0) {
            builder.append("Active!").append("\n");
        }
        else {
            builder.append("Finished!").append("\n");
        }
        builder.append("\t   ").append("Quest Level: ").append(this.questLevel).append("\n");
        builder.append("\t   ").append("Quest Item: ").append(this.questItem.getName()).append("\n");
        builder.append("\t   ").append("Quest Item Count: ").append(this.itemCount).append("\n");
        builder.append("\t   ").append("Quest State: ");
        builder.append("\t   ").append("Quest Reward: ").append(this.rewardItem.getName()).append("\n");
        builder.append("\t   ").append("Quest Reward Count: ").append(this.rewardCount).append("\n");
        return builder.toString();
    }
}
