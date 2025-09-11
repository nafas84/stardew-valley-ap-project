package io.Ap.StardewValley.Common.Model.Player;

import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.FoodRecipe;
import io.Ap.StardewValley.Common.Model.Crafting.CraftRecipe;
import io.Ap.StardewValley.Common.Model.Interaction.Friend;
import io.Ap.StardewValley.Common.Model.Interaction.Trade;
import io.Ap.StardewValley.Common.Model.Interaction.Gift;

import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.FarmBuilding;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Tool.*;
import io.Ap.StardewValley.Common.Model.Tool.*;
import io.Ap.StardewValley.Common.Model.User;
import com.google.gson.Gson;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.DirectionType;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.StateType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map;

public class Player {
    private String username;
    private float xLibGdx;
    private float yLibGdx;

    private String hairColor, pantColor;
    private int pantIndex, shirtIndex, hairIndex;

    private DirectionType direction;
    private StateType state;

    private int id;
    private int farm; // Farm id 1-4

    private Coordinate coordinate;
    private Coordinate houseCoordinate;

    private ShippingBin shippingBin = new ShippingBin();

    private List<Animal> myAnimals = new ArrayList<>();
    private List<FarmBuilding> myFarmBuildings = new ArrayList<>();

    private int maxEnergy = 200;
    private int energy = 200;
    private int maxMovesInTurn = 15;
    private int movesThisTurn = 0;

    private Inventory inventory = new Inventory(12, 1);

    private HashMap<Skill, Integer> myAbility = new HashMap<>(Map.of(Skill.Mining, 0, Skill.Foraging, 0,
            Skill.Fishing, 0, Skill.Farming, 0));

    private int count;

    //todo: refrigerator capacity? 36?
    private Inventory refrigerator = new Inventory(36, 1);

    private HashMap<Skill, Integer> skillBuff = new HashMap<>(Map.of(Skill.Mining, 0, Skill.Foraging, 0,
            Skill.Fishing, 0, Skill.Farming, 0));
    private int maxEnergyBuff = 0;

    private ArrayList<CraftRecipe> craftRecipes = new ArrayList<>(List.of(CraftRecipe.Furnace,
            CraftRecipe.Scarecrow, CraftRecipe.MayonnaiseMachine));

    private ArrayList<FoodRecipe> foodRecipes = new ArrayList<>(List.of(FoodRecipe.FriedEgg,
            FoodRecipe.BakedFish, FoodRecipe.Salad));


    private Tool currentTool;

    private ArrayList<Friend> friends = new ArrayList<>();
    private ArrayList<String> notifications = new ArrayList<>();
    private ArrayList<Gift> gifts = new ArrayList<>();
    private int numberOfGiftsSent = 0;
    private int partnerID = -1;
    private ArrayList<Trade> sentTrades = new ArrayList<>();
    private ArrayList<Trade> receivedTrades = new ArrayList<>();


    public Player() {} //needed for jason

    public Player(String hairColor, String pantColor, int pantIndex, int shirtIndex, int hairIndex, int id, int farm) {
        this.hairColor = hairColor;
        this.pantColor = pantColor;
        this.pantIndex = pantIndex;
        this.shirtIndex = shirtIndex;
        this.hairIndex = hairIndex;

        this.direction = DirectionType.Down;
        this.state = StateType.Idle;

        this.id = id;
        this.farm = farm;
        // Phase 1:
        switch (farm) {
            case 1:
                this.houseCoordinate = new Coordinate(15, 65);
                break;
            case 2:
                this.houseCoordinate = new Coordinate(15, 65 + 210);
                break;
            case 3:
                this.houseCoordinate = new Coordinate(15 + 175, 65 + 210);
                break;
            case 4:
                this.houseCoordinate = new Coordinate(15 + 175, 65);
                break;
            default:
                throw new IllegalArgumentException("Invalid player farm");
        }

        this.coordinate = new Coordinate(houseCoordinate.getX(), houseCoordinate.getY());
        setLibGdxPositionFromCoordinate();


        inventory.addItem(new Hoe(ToolLevel.Starter), 1);
        inventory.addItem(new Pickaxe(ToolLevel.Starter), 1);
        inventory.addItem(new Axe(ToolLevel.Starter), 1);
        inventory.addItem(new WateringCan(ToolLevel.Starter), 1);
        inventory.addItem(new Scythe(), 1);
        inventory.addItem(new MilkPail(), 1);

        setUsername();
    }

    public void setLibGdxPositionFromCoordinate() {
        this.xLibGdx = 1042;
        this.yLibGdx = 798;
    }

    public void setUsername() {
        File usersFolder = new File("data/users");
        File[] userFiles = usersFolder.listFiles((dir, name) -> name.endsWith(".json"));

        if (userFiles != null) {
            Gson gson = new Gson();
            for (File userFile : userFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                    User user = gson.fromJson(reader, User.class);
                    if (user != null && user.getId() == this.id)
                        this.username = user.getUsername();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("ridi");
                }
            }
        }
    }

    public int getAbilityLevel (Skill skill) {
        return Math.min(4, Math.max(0, (this.myAbility.get(skill) - 50) / 100));
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public Coordinate getHouseCoordinate() {
        return houseCoordinate;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > maxEnergy) {
            this.energy = maxEnergy;
        }
        else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean addItemToInventory (Item item, int quantity) {
        return inventory.addItem(item, quantity);
    }


    public boolean removeItemFromInventory(String itemName, int quantity) {
        return inventory.removeItem(itemName, quantity);
    }

    public boolean removeItemFromRefrigerator(String itemName, int quantity) {
        return refrigerator.removeItem(itemName, quantity);
    }

    public boolean moveItemFromInventoryToTrash(String itemName, int quantity) {
        int price = 0;
        Inventory.ItemStack stack = inventory.getItems().get(itemName.toLowerCase());

        if (stack != null) {
            if (quantity != -1) {
                int removableCount = Math.min(quantity, stack.getCount());
                for (int i = 0; i < removableCount; i++) {
                    Item item = stack.getItems().get(stack.getItems().size() - 1 - i);
                    price += item.getPrice();
                }
            } else {
                for (Item item : stack.getAll()) {
                    price += item.getPrice();
                }
            }

            price = price * (inventory.getTrashCanLevel() - 1) * 15 / 100;
        }

        boolean result = inventory.removeItem(itemName, quantity);
        if (result) {
            addCount(price);
        }
        return result;
    }


    public void setInventoryCapacity(int capacity) {
        inventory.setCapacity(capacity);
    }

    public double getInventoryCapacity () {
        return inventory.getCapacity();
    }

    public String getUsername() {
        return username;
    }

    public List<Animal> getMyAnimals() {
        return myAnimals;
    }

    public int getFarm() {
        return farm;
    }

    public void addAbility (Skill skill, int value) {
        int currentValue = myAbility.get(skill);
        currentValue += value;
        myAbility.put(skill, Math.max(currentValue, 0));
    }

    public void addSkillBuff (Skill skill, int value) {
        for (Skill s : skillBuff.keySet()) {
            skillBuff.put(s, 0);
        }
        maxEnergy = 200;
        skillBuff.put(skill, value);
    }

    public void reduceBuff (int value) {
        for (Skill s : skillBuff.keySet()) {
            int currentValue = skillBuff.get(s);
            currentValue -= value;
            skillBuff.put(s, Math.max(currentValue, 0));
        }
        maxEnergyBuff -= value;
        if (maxEnergyBuff <= 0) {
            maxEnergy = 200;
            maxEnergyBuff = 0;
        }
    }

    public boolean isBuffed (Skill s) {
        return (skillBuff.get(s) > 0);
    }

    public void addMaxEnergyBuff (int time, int value){
        maxEnergyBuff += time;
        maxEnergy += value;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxMovesInTurn() {
        return maxMovesInTurn;
    }

    public int getMovesThisTurn() {
        return movesThisTurn;
    }

    public void setMaxMovesInTurn(int maxMovesInTurn) {
        this.maxMovesInTurn = maxMovesInTurn;
    }

    public void addMovesThisTurn() {
        this.movesThisTurn ++;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public void resetMovesThisTurn() {
        this.movesThisTurn = 0;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int count) {
        addPartnerCount(count);
        this.count += count;
    }

    public void addPartnerCount(int count) {
        if (partnerID == -1)
            return;
        Player partner = App.getGame().getPlayerByID(partnerID);
        partner.count += count;
    }

    public boolean isMyFarm(Coordinate coordinate) {
        if (isMyPartnerFarm(coordinate)) {
            return true;
        }
        return switch (farm) {
            case 1 -> coordinate.getX() < 65 && coordinate.getY() < 80
                    && coordinate.getX() >= 0 && coordinate.getY() >= 0;
            case 2 -> coordinate.getX() < 65 && coordinate.getY() < 290
                    && coordinate.getX() >= 0 && coordinate.getY() >= 210;
            case 3 -> coordinate.getX() < 240 && coordinate.getY() < 290
                    && coordinate.getX() >= 175 && coordinate.getY() >= 210;
            case 4 -> coordinate.getX() < 240 && coordinate.getY() < 80
                    && coordinate.getX() >= 175 && coordinate.getY() >= 0;
            default -> throw new IllegalArgumentException("Invalid player farm");
        };
    }

    public boolean isMyPartnerFarm(Coordinate coordinate) {
        if (partnerID == -1)
            return false;
        Player partner = App.getGame().getPlayerByID(partnerID);
        return switch (partner.getFarm()) {
            case 1 -> coordinate.getX() < 65 && coordinate.getY() < 80
                    && coordinate.getX() >= 0 && coordinate.getY() >= 0;
            case 2 -> coordinate.getX() < 65 && coordinate.getY() < 290
                    && coordinate.getX() >= 0 && coordinate.getY() >= 210;
            case 3 -> coordinate.getX() < 240 && coordinate.getY() < 290
                    && coordinate.getX() >= 175 && coordinate.getY() >= 210;
            case 4 -> coordinate.getX() < 240 && coordinate.getY() < 80
                    && coordinate.getX() >= 175 && coordinate.getY() >= 0;
            default -> throw new IllegalArgumentException("Invalid player farm");
        };
    }

    public void addAnimal(Animal animal) {
        this.myAnimals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        this.myAnimals.remove(animal);
    }

    @Override
    public String toString() {
        String result = "Player Info:\n" +
                "________________________________\n" +
                "Username: " + getUsername() + "\n" +
                "Id: " + this.id + "\n" +
                "Farm id: " + this.farm + "\n" +
                "Coordinate: (" + this.coordinate.getX() + ", " + this.coordinate.getY() + ")\n" +

                //"Max moves in this turn: " + this.maxMovesInTurn + "\n" +
                "Moves in this Turn: " + this.movesThisTurn + "\nmax moves in a turn: ";

        if (maxMovesInTurn >= Integer.MAX_VALUE - 200) {
            result = result + "unlimited";
        }
        else {
            result = result + maxMovesInTurn;
        }

        result = result + "\nCount: " + this.count + "\n" +

                "Energy: " + this.energy + "\nEnergy limit: ";
        if (maxEnergy == Integer.MAX_VALUE) {
            result = result + "unlimited";
        }
        else {
            result = result + maxEnergy;
        }
        if (maxEnergyBuff > 0) {
            result = result + "\nmax energy buff is activated.";
        }
        for (Skill s : Skill.values()) {
            if (isBuffed(s)) {
                result = result + "\n" + s.name() + " buff is activated.";
            }
        }

        return result;
    }

    public ArrayList<CraftRecipe> getCraftRecipes() {
        return craftRecipes;
    }

    public boolean addToCraftRecipes(CraftRecipe recipe) {
        if (craftRecipes.contains(recipe)) {
            return false;
        }
        craftRecipes.add(recipe);
        return true;
    }

    public List<FarmBuilding> getMyFarmBuildings() {
        return myFarmBuildings;
    }

    public void addToFarmBuildings (FarmBuilding building) {
        for (FarmBuilding b : myFarmBuildings) {
            if (b.getType().getType() == building.getType().getType()) {
                myFarmBuildings.remove(b);
                myFarmBuildings.add(building);
                return;
            }
        }
        myFarmBuildings.add(building);
    }

    public int getFarmBuildingLevel (BuildingType type) {
        int max = 0;
        for (FarmBuilding b : myFarmBuildings) {
            if (b.getType().getType() == type) {
                if (b.getCapacity() > max) {
                    max = b.getCapacity();
                }
            }
        }
        return max;
    }

    public int getFarmBuildingCapacity (BuildingType type) {
        int animalNumber = 0;
        for (Animal a : myAnimals) {
            if (a.getType().getFarmBuildingType().getType() == type) {
                animalNumber++;
            }
        }
        return Math.max(0, getFarmBuildingLevel(type) * 4 - animalNumber);
    }

    public ArrayList<FoodRecipe> getFoodRecipes() {
        return foodRecipes;
    }

    public boolean addToFoodRecipes (FoodRecipe recipe) {
        if (foodRecipes.contains(recipe)) {
            return false;
        }
        foodRecipes.add(recipe);
        return true;
    }

    public void addItemToShippingBin(Item item, int count) {
        this.shippingBin.add(item, count);
    }

    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public int getInventoryItemCount (Item item) {
        return inventory.getItemQuantity(item);
        // if isn't available returns -1
    }

    public int getTotalSkills() {
        int totalSkills = 0;
        for (Skill skill: Skill.values())
            totalSkills += getAbilityLevel(skill);

        return totalSkills / Skill.values().length;
    }

    public Inventory getRefrigerator() {
        return refrigerator;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void addNotification (String message) {
        this.notifications.add(message);
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void setNumberOfGiftsSent(int numberOfGiftsSent) {
        this.numberOfGiftsSent = numberOfGiftsSent;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
    }

    public int getNumberOfGiftsSent() {
        return numberOfGiftsSent;
    }

    public ArrayList<Item> getAndRemoveItemsFromInventory (int n, String itemName) {
        return inventory.getAndRemoveItems(n, itemName);
    }

    public void addGiftToGifts (Gift gift) {
        gifts.add(gift);
    }

    public void addGiftToInventory (Gift gift) {
        for (Item item : gift.getGift()) {
            this.addItemToInventory(item, 1);
        }
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public int getPartnerID() {
        return partnerID;
    }

    public void addSentTrade (Trade trade) {
        sentTrades.add(trade);
    }

    public void addReceivedTrade (Trade trade) {
        receivedTrades.add(trade);
    }

    public ArrayList<Trade> getSentTrades() {
        return sentTrades;
    }

    public ArrayList<Trade> getReceivedTrades() {
        return receivedTrades;
    }

    public DirectionType getDirection() {
        return direction;
    }

    public StateType getState() {
        return state;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public String getPantColor() {
        return pantColor;
    }

    public int getPantIndex() {
        return pantIndex;
    }

    public int getShirtIndex() {
        return shirtIndex;
    }

    public int getHairIndex() {
        return hairIndex;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setXLibGdx(float xLibGdx) {
        this.xLibGdx = xLibGdx;
    }

    public void setYLibGdx(float yLibGdx) {
        this.yLibGdx = yLibGdx;
    }

    public float getXLibGdx() {
        return xLibGdx;
    }

    public float getYLibGdx() {
        return yLibGdx;
    }

    public void setFarmingLevel (int level) {
        myAbility.put(Skill.Farming, level);
    }
    public void setFishingLevel (int level) {
        myAbility.put(Skill.Fishing, level);
    }
    public void setForagingLevel (int level) {
        myAbility.put(Skill.Foraging, level);
    }
    public void setMiningLevel (int level) {
        myAbility.put(Skill.Mining, level);
    }
}
