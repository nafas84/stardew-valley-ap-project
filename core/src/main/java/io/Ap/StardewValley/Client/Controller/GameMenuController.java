package io.Ap.StardewValley.Client.Controller;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.NightController;
import io.Ap.StardewValley.Gson.ItemAdapter;
import io.Ap.StardewValley.Gson.ShopAdapter;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Command.Menu;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Game;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.GameMap;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.BlackSmith.BlackSmith;
import io.Ap.StardewValley.Common.Model.Shop.CarpentersShop.CarpentersShop;
import io.Ap.StardewValley.Common.Model.Shop.FishShop.FishShop;
import io.Ap.StardewValley.Common.Model.Shop.JojaMart.JojaMart;
import io.Ap.StardewValley.Common.Model.Shop.MarniesRanch.MarniesRanch;
import io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore.PierresStore;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon.TheStardropSaloon;
import io.Ap.StardewValley.Common.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameMenuController {
    public static final int[] farmSelections = new int[4];
    private static final boolean[] deleteGame = new boolean[4];

    private static final String URLSqlite = "jdbc:sqlite:data/games.db";

    public static Result newGame(String username1, String username2, String username3) {
        if (hasSavedGame(App.getCurrentUser().getId())) {
            return new Result(false, "You have a saved game. You can't create new one!");
        } else if (App.getGame() != null) {
            return new Result(false, "You are in game. Boro khodeto siah kon");
        }
        Player currentPlayer = new Player("Black", "Black", 0, 0, 0, App.getCurrentUser().getId(), 1);
        ArrayList<Player> players = new ArrayList<>();
        players.add(currentPlayer);

        if (username1 == null) {
            return new Result(false, "You should start the game with at least one more player!");
        }

        User user1 = App.getUserByUsername(username1);
        if (user1 == null)
            return new Result(false, username1 + " not found!");
        if (hasSavedGame(user1.getId())) {
            return new Result(false, username1 + " have a saved game. You can't create new one!");
        }
        players.add(new Player("Black", "Black", 0, 0, 0, user1.getId(), 2));

        if (username2 != null) {
            User user2 = App.getUserByUsername(username2);
            if (user2 == null)
                return new Result(false, username2 + " not found!");
            if (hasSavedGame(user2.getId())) {
                return new Result(false, username2 + " have a saved game. You can't create new one!");
            }
            players.add(new Player("Black", "Black", 0, 0, 0, user2.getId(), 3));
        }


        if (username3 != null) {
            User user3 = App.getUserByUsername(username3);
            if (user3 == null)
                return new Result(false, username3 + " not found!");
            if (hasSavedGame(user3.getId())) {
                return new Result(false, username3 + " have a saved game. You can't create new one!");
            }
            players.add(new Player("Black", "Black", 0, 0, 0, user3.getId(), 4));
        }

        App.setGame(new Game(players, currentPlayer, currentPlayer));
        return new Result(true, "Game started successfully. Now you should choose your map:");
    }

    public static Result chooseMap(int i ,int farm) {
        if (farm < 1 || farm > 2) {
            return new Result(false, "Invalid number. Please enter a number between 1 and 2:");
        }

        GameMenuController.farmSelections[i] = farm;
        return new Result(true, "Benazam.");
    }

    public static Result loadNewGame() {
        for (int i = App.getGame().getPlayers().size(); i < 4; i++) {
            farmSelections[i] = 1;
        }

        App.getGame().setShops(new ArrayList<>(List.of(new BlackSmith(), new CarpentersShop(), new FishShop(),
                new JojaMart(), new MarniesRanch(), new PierresStore(), new TheStardropSaloon())));

        App.getGame().setMap(new GameMap(farmSelections));
        App.getGame().getMap().setFulMap();
        //App.getGame().setNPCs();
        //App.getGame().setFriends();

        NightController.randomForagingPlants();
        NightController.randomForagingMinerals();
        NightController.randomStoneWood();

        return new Result(true, "Game loaded. Now you are in the game. Boro eshgh kon.");
    }

    public static Result loadGame() {
        int id = App.getCurrentUser().getId();
        if (!hasSavedGame(id)) {
            return new Result(false, "You don't have any saved game to load!");
        }

        Game game = getGameById(id);
        App.setGame(game);
        Player player = getPlayer(id);
        if (player == null) {
            return new Result(false, "Sorry something went wrong!");
        }
        App.getGame().setMainPlayer(player);
        App.getGame().getMap().setFulMap();
        return new Result(true, "Game successfully loaded.");
    }

    public static Result loadGameFromDB(String name) {
        // TODO:
        String sql = "SELECT json FROM games WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(URLSqlite);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                String json = rs.getString("json");

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Item.class, new ItemAdapter())
                        .registerTypeAdapter(Shop.class, new ShopAdapter())
                        .create();

                Game game = gson.fromJson(json, Game.class);
                if (game == null) {
                    return new Result(false, "Failed to parse saved game.");
                }

                App.setGame(game);

                Player player = getPlayer(App.getCurrentUser().getId());
                if (player == null) {
                    return new Result(false, "Sorry, something went wrong(database)");
                }
                App.getGame().setMainPlayer(player);
                App.getGame().getMap().setFulMap();

                return new Result(true, "Game successfully loaded from database.");
            } else {
                return new Result(false, "No saved game found with this name(database)");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return new Result(false, "Database error occurred.");
        }
    }


    public static Result exitGame() {
        if (App.getGame() == null) {
            return new Result(false, "You should be in a game!");
        } else if (App.getGame().getCurrentPlayer().getId() != App.getGame().getMainPlayer().getId()) {
            return new Result(false, "Just main player(who created the game or last loaded it) can use the following command!");
        }

        StringBuilder name = new StringBuilder();
        for(Player player: App.getGame().getPlayers())
            name.append(player.getId()).append("_");
        if (!name.isEmpty()) name.deleteCharAt(name.length() - 1);

        saveGame(name.toString());
        saveGameToDB(name.toString());

        App.setGame(null);
        App.setCurrentMenu(Menu.MainMenu);

        return new Result(true, "Game saved successfully. Now you are in Main menu");
    }

    public static Result deleteGame(int i, String vote) {
        if (!vote.equalsIgnoreCase("yes") && !vote.equalsIgnoreCase("no")) {
            return new Result(false, "Just use yes or no, stupid.");
        }

        deleteGame[i] = vote.equalsIgnoreCase("yes");
        return new Result(true, "Benazam.");
    }

    public static Result resultDeleteGame() {
        if (allPlayersVotedYes()) {

            deleteGame(App.getGame().getCurrentPlayer().getId());

            App.setGame(null);
            App.setCurrentMenu(Menu.MainMenu);
            return new Result(true, "Bazi hazf shod. Mobarak kheilia");
        } else {
            return new Result(false, "All players must agree to delete the game!");
        }
    }

    private static void saveGame(String name) {
        File fileName = new File("data/games/" + name + ".json");

        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Item.class, new ItemAdapter())
                    .registerTypeAdapter(Shop.class, new ShopAdapter())
                    //.setPrettyPrinting()
                    .create();
            gson.toJson(App.getGame(), writer);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static void saveGameToDB(String name) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, new ItemAdapter())
                .registerTypeAdapter(Shop.class, new ShopAdapter())
                .create();

        String json = gson.toJson(App.getGame());

        String sql = "INSERT OR REPLACE INTO games(name, json) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(URLSqlite);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, json);
            pstmt.executeUpdate();

            System.out.println("Game saved to database successfully.");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Game getGameById(int id) {
        File folder = new File("data/games");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Games folder not found!");
            return null;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return null;

        for (File file : files) {
            String fileName = file.getName();
            String baseName = fileName.substring(0, fileName.length() - 5); // delete json
            String[] ids = baseName.split("_");

            for (String sId : ids) {
                try {
                    int fileId = Integer.parseInt(sId);
                    if (fileId == id) {
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(Item.class, new ItemAdapter())
                                .registerTypeAdapter(Shop.class, new ShopAdapter())
                                .create();
                        try (FileReader reader = new FileReader(file)) {
                            return gson.fromJson(reader, Game.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("اکهی");
                }
            }
        }

        return null;
    }

    private static boolean hasSavedGame(int id) {
        File folder = new File("data/games");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Games folder not found!");
            return false;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return false;

        for (File file : files) {
            String fileName = file.getName();
            String baseName = fileName.substring(0, fileName.length() - 5); // delete json
            String[] ids = baseName.split("_");

            for (String sId : ids) {
                try {
                    int fileId = Integer.parseInt(sId);
                    if (fileId == id) {
                        return true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("اکهی");
                }
            }
        }

        return false;
    }

    private static void deleteGame(int playerId) {
        File gamesFolder = new File("data/games");
        if (!gamesFolder.exists() || !gamesFolder.isDirectory()) {
            return;
        }

        File[] files = gamesFolder.listFiles();
        if (files == null) return;

        for (File file : files) {
            String filename = file.getName(); //format: id1_id2_id3.json
            String nameWithoutExtension = filename.contains(".") ? filename.substring(0, filename.lastIndexOf('.')) : filename;
            String[] ids = nameWithoutExtension.split("_");

            for (String idStr : ids) {
                try {
                    int id = Integer.parseInt(idStr);
                    if (id == playerId) {
                        file.delete();
                        return;
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println("اکهی پسر");
                }
            }
        }
    }

    // --------------------------------------------------------------------------------------------------------------
    public static Result nextTurn() {
        // BUG: وقتی نفر اخر انرژیش صفر شه  هیچ موقع نمیره ساعت بعدی.....
        // BUG: نکست ترن زد و شب شد باید بره نفر بعدی که نمیره
        int index = App.getGame().getPlayers().indexOf(App.getGame().getCurrentPlayer());
        int totalPlayers = App.getGame().getPlayers().size();
        int nextIndex = (index + 1) % totalPlayers;

        // update time
        if (nextIndex == 0) {

            App.getGame().getCurrentTime().addHour(1);
            if (App.getGame().getCurrentTime().getHour() == 24) {
                NightController.nightControl();
                return new Result(true, "Shab bekheir...");
            }
        }

        // skip players with 0 energy:
        int startingIndex = nextIndex;
        do {
            Player candidate = App.getGame().getPlayers().get(nextIndex);
            if (candidate.getEnergy() > 0) {
                App.getGame().setCurrentPlayer(candidate);
                App.getGame().getCurrentPlayer().resetMovesThisTurn();
                return new Result(true, "Now it's " + candidate.getUsername() + "'s turn.");
            }
            nextIndex = (nextIndex + 1) % totalPlayers;
        } while (nextIndex != startingIndex);

        NightController.nightControl();
        return new Result(true, "Nobody had energy. Skipping to night.");
    }

    public static Result gotoNextDay() {
        NightController.nightControl();
        return new Result(true, "Shab bekheir...");
    }

    public static Result currentMenu () {
        return new Result(true, "You are in game menu");
    }

    public static Result currentPlayer() {
        return new Result(true, App.getGame().getCurrentPlayer().toString());
    }

    public static Coordinate getCoordinateByDirection (String direction) {
        direction = direction.toLowerCase();
        Coordinate coordinate = new Coordinate(App.getGame().getCurrentPlayer().getCoordinate().getX(),
                App.getGame().getCurrentPlayer().getCoordinate().getY());
        int x = coordinate.getX();
        int y = coordinate.getY();

        int maxX = 240;
        int maxY = 290;

        switch (direction) {
            case "n":
                if (x > 0) {
                    coordinate.setX(x - 1);
                } else {
                    return null;
                }
                break;
            case "s":
                if (x < maxX) {
                    coordinate.setX(x + 1);
                } else {
                    return null;
                }
                break;
            case "e":
                if (y < maxY) {
                    coordinate.setY(y + 1);
                } else {
                    return null;
                }
                break;
            case "w":
                if (y > 0) {
                    coordinate.setY(y - 1);
                } else {
                    return null;
                }
                break;
            case "ne":
                if (x > 0 && y < maxY) {
                    coordinate.setX(x - 1);
                    coordinate.setY(y + 1);
                } else {
                    return null;
                }
                break;
            case "nw":
                if (x > 0 && y > 0) {
                    coordinate.setX(x - 1);
                    coordinate.setY(y - 1);
                } else {
                    return null;
                }
                break;
            case "se":
                if (x < maxX && y < maxY) {
                    coordinate.setX(x + 1);
                    coordinate.setY(y + 1);
                } else {
                    return null;
                }
                break;
            case "sw":
                if (x < maxX && y > 0) {
                    coordinate.setX(x + 1);
                    coordinate.setY(y - 1);
                } else {
                    return null;
                }
                break;
            default:
                return null;
        }
        return coordinate;
    }

    public static Player getPlayer(int id) {
        for (Player player: App.getGame().getPlayers()) {
            if (player.getId() == id)
                return player;
        }
        return null;
    }

    public static void moveControl () {
        App.getGame().getCurrentPlayer().addMovesThisTurn();
    }

    private static boolean allPlayersVotedYes() {
        int playerCount = App.getGame().getPlayers().size();
        for (int i = 1; i < playerCount; i++) {
            if (!deleteGame[i]) {
                return false;
            }
        }
        return true;
    }

    public static void hourControl () {
        Player player = App.getGame().getCurrentPlayer();
        player.reduceBuff(1);

        outer:
        for (CraftType r : CraftType.values()) {
            if (player.getCraftRecipes().contains(r.getIngredient())) continue;
            if (r.getLevel() == null) continue;
            for (Skill s : r.getLevel().keySet()) {
                if (player.getAbilityLevel(s) < r.getLevel().get(s)) {
                    continue outer;
                }
            }
            player.addToCraftRecipes(r.getIngredient());
        }

        outer:
        for (FoodType r : FoodType.values()) {
            if (player.getFoodRecipes().contains(r.getRecipe())) continue;
            if (r.getLevel() == null) continue;
            for (Skill s : r.getLevel().keySet()) {
                if (player.getAbilityLevel(s) < r.getLevel().get(s)) {
                    continue outer;
                }
            }
            player.addToFoodRecipes(r.getRecipe());
        }
    }

    public static void printMessagesReceived() {
        ArrayList<String> messages = App.getGame().getCurrentPlayer().getNotifications();
        if (messages.isEmpty()) {
            System.out.println("You have not received any notifications!");
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("You have received ").append(messages.size()).append(" notifications!\n\n");
        builder.append("Messages:\n\n");
        int counter = 0;
        for (String notification : messages) {
            counter++;
            builder.append("\t").append(counter).append("- ").append(notification).append("\n");
        }
        System.out.println(builder.toString());
    }

    public static Tile getTileByDirection (String direction) {
        direction = direction.toLowerCase();
        Coordinate coordinate = new Coordinate(App.getGame().getCurrentPlayer().getCoordinate().getX(),
                App.getGame().getCurrentPlayer().getCoordinate().getY());
        int x = coordinate.getX();
        int y = coordinate.getY();
        // Phase 1:
        int maxX = 240;
        int maxY = 290;

        switch (direction) {
            case "n":
                if (x > 0) {
                    coordinate.setX(x - 1);
                } else {return null;}
                break;
            case "s":
                if (x < maxX) {
                    coordinate.setX(x + 1);
                } else {return null;}
                break;
            case "e":
                if (y < maxY) {
                    coordinate.setY(y + 1);
                } else {return null;}
                break;
            case "w":
                if (y > 0) {
                    coordinate.setY(y - 1);
                } else {return null;}
                break;
            case "ne":
                if (x > 0 && y < maxY) {
                    coordinate.setX(x - 1);
                    coordinate.setY(y + 1);
                } else {return null;}
                break;
            case "nw":
                if (x > 0 && y > 0) {
                    coordinate.setX(x - 1);
                    coordinate.setY(y - 1);
                } else {return null;}
                break;
            case "se":
                if (x < maxX && y < maxY) {
                    coordinate.setX(x + 1);
                    coordinate.setY(y + 1);
                } else {return null;}
                break;
            case "sw":
                if (x < maxX && y > 0) {
                    coordinate.setX(x + 1);
                    coordinate.setY(y - 1);
                } else {return null;}
                break;
            default:
                return null;
        }

        return App.getGame().getTile(coordinate);
    }

    // Phase 2:
    public static void newGameOffline (String hairColor, String pantColor, int pantIndex, int shirtIndex, int hairIndex, int farmIdSelect) {
        Player currentPlayer = new Player(hairColor, pantColor, pantIndex, shirtIndex, hairIndex, App.getCurrentUser().getId(), 1);
        ArrayList<Player> players = new ArrayList<>();
        players.add(currentPlayer);

        GameMenuController.farmSelections[0] = farmIdSelect;

        App.setGame(new Game(players, currentPlayer, currentPlayer));
        loadNewGame();
    }
}
