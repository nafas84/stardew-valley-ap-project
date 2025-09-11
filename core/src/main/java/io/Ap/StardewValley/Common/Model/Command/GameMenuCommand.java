package io.Ap.StardewValley.Common.Model.Command;

public enum GameMenuCommand implements Command {
    // Game Controller: Nafiseh
    CurrentMenu ("show\\s+current\\s+menu"),
    CurrentPlayer("show\\s+current\\s+player"),
    NewGame("new\\s+game\\s+-u(?:\\s+(?<username1>\\S+))?(?:\\s+(?<username2>\\S+))?(?:\\s+(?<username3>\\S+))?"),
    ChooseMap("game\\s+map\\s+(?<mapNumber>[-+]?\\d+)"),
    LoadGame("load\\s+game"),
    ExitGame("exit\\s+game"),
    DeleteGame("delete\\s+game"),
    NextTurn("next\\s+turn"),
    GotoNextDay("go\\s+to\\s+next\\s+day"),

    // Time Controller: Nafiseh
    Time("time|date|datetime|day of the week"),
    CheatTime("cheat\\s+time\\s+(?<time>[-+]?\\d+)h"),
    CheatDate("cheat\\s+date\\s+(?<date>[-+]?\\d+)d"),

    // Time Controller: Nafiseh
    Season("season"),
    Weather("weather"),
    WeatherForecast("weather\\s+forecast"),
    CheatThor("cheat\\s+Thor\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"), // todo
    CheatWeather("cheat\\s+weather\\s+set\\s+(?<type>\\S+)"),

    // Map Controller: Nafiseh
    BuildGreenhouse("build\\s+greenhouse"), // todo

    Walk("walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"),
    PrintAllMap("print\\s+all\\s+map"),
    PrintMap("print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)"),
    PrintFarm("print\\s+my\\s+farm"),
    HelpMap("help\\s++map"),
    TileInfo("tile\\s+info\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"),

    // Player Controller: Aynaz
    ShowEnergy("show\\s+energy"),
    CheatEnergy("cheat\\s+set\\s+energy\\s+-v\\s+(?<value>[-+]?\\d+)"),
    CheatUnlimitedEnergy("unlimited\\s+energy"),

    // Player Controller: Aynaz
    ShowInventory("show\\s+inventory"),
    InventoryTrashWithNumber("inventory\\s+trash\\s+-i\\s+(?<itemName>.+?)\\s+-n\\s+(?<number>[-+]?\\d+)"),
    InventoryTrashWithoutNumber("inventory\\s+trash\\s+-i\\s+(?<itemName>.+)"),
    ShowAbility("show\\s+ability"),

    CheatAddItem("cheat\\s+add\\s+item\\s+-t\\s+(?<type>.+?)\\s+-n\\s+(?<name>.+?)\\s+-c\\s+(?<count>\\d+)"),

    // Tool Controller: Aynaz
    EquipTool("tools\\s+equip\\s+(?<toolName>.+)"),
    ShowCurrentTool("tools\\s+show\\s+current"),
    ShowAvailableTool("tools\\s+show\\s+available"),
    UpgradeTool("tools\\s+upgrade\\s+(?<toolName>.+)"),
    UseTool("tools\\s+use\\s+-d\\s+(?<direction>.+)"), //

    // Plant Controller: Aynaz
    CropInfo("craft\\s*info\\s+-n\\s+(?<craftName>.+?)"),
    Plant("plant\\s+-s\\s+(?<seed>.+?)\\s+-d\\s+(?<direction>\\S+)"),
    ShowPlant("show plant\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"),
    Fertilize("fertilize\\s+-f\\s+(?<fertilizer>.+?)\\s+-d\\s+(?<direction>\\S+)"),
    ShowWater("how\\s+much\\s+water"),

    // Craft Controller: Aynaz
    ShowCraftingRecipes("crafting\\s+show\\s+recipes"),
    Crafting("crafting\\s+craft\\s+(?<itemName>.+)"),
    PlaceItem("place\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-d\\s+(?<direction>\\S+)"),
    CheatCrafting("cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-c\\s+(?<count>\\d+)\\s+(?<type>.+)"),

    // Food Controller: Aynaz
    PutRefrigerator("cooking\\s+refrigerator\\s+put\\s+(?<item>.+)"),
    PickRefrigerator("cooking\\s+refrigerator\\s+pick\\s+(?<item>.+)"),
    ShowCookingRecipes("cooking\\s+show\\s+recipes"),
    Cooking("cooking\\s+prepare\\s+(?<recipeName>.+)"),
    Eat("eat\\s+(?<foodName>.+)"),

    // Map Controller: Nafiseh
    Build("build\\s+-a\\s+(?<name>.+?)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"),
    ShowListFarmBuilding("show\\s+list\\s+farm\\s+building"),

    // Animal Controller: Parsa -> Nafiseh
    BuyAnimal("buy\\s+animal\\s+-a\\s+(?<animal>.+?)\\s+-n\\s+(?<name>.+)"),
    Pet("pet\\s+-n\\s+(?<name>.+)"),
    ShowAnimalsInfo("animals\\s+list"),
    CheatFriendShipAnimal("cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+?)\\s+-c\\s+(?<amount>\\d+)"),
    ShepherdAnimal("shepherd\\s+animals\\s+-n\\s+(?<name>.+?)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)"), // todo
    FeedAnimal("feed\\s+hay\\s+-n\\s+(?<name>.+)"),
    ShowAnimalProduceInfo("animal\\s+produces\\s+list"),
    CollectAnimalProduce("collect\\s+produce\\s+-n\\s+(?<name>.+?)"),
    SellAnimal("sell\\s+animal\\s+-n\\s+(?<name>.+)"),

    Fishing("fishing\\s+-p\\s+(?<fishingPole>.+)"), // todo -> use tool

    // Artisan Controller: Aynaz (optional)
    ArtisanUse("artisan\\s+use\\s+-a\\s+(?<artisanName>.+?)\\s+-i\\s+(?<item1Name>.+)"),
    ArtisanGet("artisan\\s+get\\s+(?<artisanName>.+)"),

    // Shop Controller: Parsa -> Nafiseh, Aynaz
    ShowShopProduct("show\\s+all\\s+products"),
    ShowShopAvailableProduct("show\\s+all\\s+available\\s+products"),
    PurchaseWithNumber("purchase\\s+(?<productName>.+?)\\s+-n\\s+(?<count>[-+]?\\d+)"),
    Purchase("purchase\\s+(?<productName>.+)"),
    CheatAddCount("cheat\\s+add\\s+(?<count>\\d+)\\s+dollars"),
    SellProduct("sell\\s+-t\\s+(?<type>.+?)\\s+-n\\s+(?<name>.+?)(?:\\s+-c\\s+(?<count>\\d+))?"),

    // Relation Controller: Parsa
    FriendsShipPlayerList("friendships"),
    Talk("talk\\s+-u\\s+(?<username>.+?)\\s+-m\\s+(?<message>.+)"),
    TalkHistory("talk\\s+history\\s+-u\\s+(?<username>.+)"),
    Gift("gift\\s+-u\\s+(?<username>.+)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>\\d+)"),
    GiftList("gift\\s+list"),
    GiftRate("gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d+)"),
    GiftHistory("gift\\s+history\\s+-u\\s+(?<username>.+)"),
    Hug("hug\\s+-u\\s+(?<username>.+)"),
    Flower("flower\\s+-u\\s+(?<username>.+)"),
    AskMarriage("ask\\s+marriage\\s+-u\\s+(?<username>.+?)\\s+-r\\s+(?<ring>.+)"),
    RespondMarriageAccept("respond\\s+-accept\\s+-u\\s+(?<username>.+)"),
    RespondMarriageReject("respond\\s+-reject\\s+-u\\s+(?<username>.+)"),

    // Relation Controller: Parsa
    TradeMenu("start\\s+trade"),

    // NPC Controller: Parsa -> Nafiseh
    MeetNPC("meet\\s+NPC\\s+(?<npcName>.+)\\s+-m\\s+(?<message>.*)"),
    GiftNPC("gift\\s+NPC\\s+(?<npcName>.+?)\\s+-i\\s+(?<item>.+)"),
    FriendShipNPCList("friendship\\s+NPC\\s+list"),
    QuestList("quests\\s+list"),
    QuestFinish("quests\\s+finish\\s+-i\\s+(?<index>\\d+)");

    private final String pattern;

    GameMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
