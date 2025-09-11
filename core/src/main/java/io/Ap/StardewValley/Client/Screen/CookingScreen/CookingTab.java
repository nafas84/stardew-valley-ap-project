package io.Ap.StardewValley.Client.Screen.CookingScreen;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.FoodController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.FoodRecipe;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;

import java.util.*;
import java.util.List;

public class CookingTab extends Window {
    private final Skin skin;
    private final Table recipeTable;
    private final ScrollPane recipeScrollPane; // ← NEW
    private final List<ImageTextButton> recipeButtons = new ArrayList<>();
    private final Map<ImageTextButton, FoodRecipe> recipeButtonToFoodRecipe = new HashMap<>();
    private ImageTextButton selectedRecipeButton;
    private final Stage stageForErrorDisplay;


    public CookingTab(Skin skin, Stage stageForErrorDisplay) {
        super("", skin);
        this.skin = StardewValley.getSkin();
        this.stageForErrorDisplay = stageForErrorDisplay;

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        recipeTable = new Table();
        recipeTable.top().left();
        recipeScrollPane = new ScrollPane(recipeTable, skin, "inventory");
        recipeScrollPane.setFadeScrollBars(false);

        update();

        Table rightPart = new Table();

        ImageButton cookButton = new ImageButton(skin, "trash");
        cookButton.setTransform(true);
        cookButton.scaleBy(0.4f);
        cookButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedRecipeButton == null || getSelectedRecipe() == null) {
                    showError("no item has been selected");
                    return;
                }
                Result result = FoodController.cookThroughScreen(getSelectedRecipe().getName());
                if (!result.isSuccessful()) {
                    showError(result.message());
                    return;
                }
                GameScreenController.setCookingStageNeedsUpdate(true);
            }
        });


        rightPart.add(cookButton).size(80, 80);


        Table recipeWrapper = new Table();
        recipeWrapper.add(new Label("Recipes", skin)).padBottom(10).padLeft(100).row();
        recipeWrapper.add(recipeScrollPane).width(750).height(500);


        this.add(recipeWrapper).top().pad(10);
        this.add(rightPart).width(100).center().pad(10, 10, 10, 10);

//            this.setDebug(true);
    }

    private void selectRecipeButton(int index) {
        for (int i = 0; i < recipeButtons.size(); i++) {
            recipeButtons.get(i).setChecked(i == index);
        }
        selectedRecipeButton = recipeButtons.get(index);
    }


    public void update() {

        recipeTable.clear();
        recipeButtons.clear();
        recipeButtonToFoodRecipe.clear();
        ArrayList<FoodRecipe> foodRecipes = App.getGame().getCurrentPlayer().getFoodRecipes();
        int capacity = 24;
        int fridgeCols = 6;

        for (int i = 0; i < capacity; i++) {
            final int index = i;
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                    skin.get("default", ImageTextButton.ImageTextButtonStyle.class)
            );

            if (i < foodRecipes.size()) {
                style.imageUp = new TextureRegionDrawable(ItemTextureBank.getTexture(foodRecipes.get(i).getName()));
            }

            ImageTextButton button = new ImageTextButton("", style);
            recipeButtons.add(button);

            if (i < foodRecipes.size()) {
                FoodRecipe recipe = foodRecipes.get(i);
                recipeButtonToFoodRecipe.put(button, recipe);

                TextTooltip tooltip = new TextTooltip(recipe.getName() + ":\n" + recipe.getRecipeString(), skin, "letter");
                button.addListener(tooltip);
            }

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectRecipeButton(index);
                }
            });

            recipeTable.add(button).size(110, 110).pad(6);

            try {
                button.getImageCell().height(90).width(90);
            } catch (Exception ignored) {}

            if ((i + 1) % fridgeCols == 0) recipeTable.row();
        }

    }

    private void showError(String msg) {
        final Window errorWindow = new Window("", skin, "Letter");
        errorWindow.setMovable(false);
        errorWindow.setKeepWithinStage(true);
        errorWindow.add(new Label(msg, skin));
        errorWindow.setSize(700, 90);
        errorWindow.setPosition(600, 170, Align.center);
        errorWindow.pack();

        stageForErrorDisplay.addActor(errorWindow);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorWindow.remove();
            }
        }, 5);
    }

    public FoodRecipe getSelectedRecipe() {
        return recipeButtonToFoodRecipe.get(selectedRecipeButton);
    }

}