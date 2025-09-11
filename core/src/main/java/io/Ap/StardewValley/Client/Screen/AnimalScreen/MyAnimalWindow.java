package io.Ap.StardewValley.Client.Screen.AnimalScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.App;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAnimalWindow extends Window {
    private final ScrollPane allItemsScroll;

    private final Table allItemsTable;

    private final java.util.List<ImageTextButton> allButtons = new ArrayList<>();

    private final Skin skin;

    private final Map<ImageTextButton, Animal> buttonToProduct = new HashMap<>();
    private ImageTextButton selectedButton = null;

    private boolean productDataNeedsUpdate = false;

    public MyAnimalWindow(Skin skin) {
        super("Shop Stock", skin);
        this.skin = skin;

        setSize(500, 700);
        setMovable(false);
        setResizable(false);
        setModal(false);
        setKeepWithinStage(true);

        allItemsTable = new Table(skin);

        allItemsScroll = new ScrollPane(allItemsTable, skin, "inventory");

        Stack scrollStack = new Stack();
        scrollStack.add(allItemsScroll);
        add(scrollStack).expand().fill();

        update();
    }

//    private void updateVisibleScrollPane() {
//        if (onlyAvailableCheckBox.isChecked()) {
//            allItemsScroll.setVisible(false);
//            availableItemsScroll.setVisible(true);
//        } else {
//            allItemsScroll.setVisible(true);
//            availableItemsScroll.setVisible(false);
//        }
//        // وقتی visible عوض شد، اگر selectedButton داخل آن ScrollPane نیست، باید انتخاب رو تغییر بدیم:
//        if (availableItemsScroll.isVisible()) {
//            if (selectedButton == null || !availableButtons.contains(selectedButton)) {
//                selectButtonIfExists(availableButtons);
//            }
//        } else {
//            if (selectedButton == null || !allButtons.contains(selectedButton)) {
//                selectButtonIfExists(allButtons);
//            }
//        }
//    }

    public void update() {
        allItemsTable.clear();
        allButtons.clear();
        buttonToProduct.clear();
        selectedButton = null;

        for (Animal animal : App.getGame().getCurrentPlayer().getMyAnimals()) {
            ImageTextButton btn = creatAnimalButton(animal);
            allButtons.add(btn);
            buttonToProduct.put(btn, animal);
            allItemsTable.add(btn).expandX().fillX().row();
        }

        addClickListenerToButtons();

        // انتخاب اولین دکمه لیست available به صورت پیش‌فرض

        //updateVisibleScrollPane();
        pack();
    }

    private ImageTextButton creatAnimalButton(Animal animal) {
        TextureRegion texture = BankAnimalTextures.getAvatar(animal.getType());
        Image image = new Image(texture);

        Table contentTable = new Table();

        contentTable.add(image).size(50, 50).padRight(10);

        Label nameLabel = new Label(animal.getName(), skin);
        nameLabel.setAlignment(Align.left);
        contentTable.add(nameLabel).expandX().left();

        ImageTextButton button = new ImageTextButton("", skin);
        button.clearChildren();
        button.add(contentTable).expand().fill();

        return button;
    }

    private void addClickListenerToButtons() {
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageTextButton btn = (ImageTextButton) event.getListenerActor();
                selectButton(btn);
                productDataNeedsUpdate = true;
            }
        };

        for (ImageTextButton btn : allButtons) {
            btn.addListener(listener);
        }

    }

    private void selectButton(ImageTextButton btn) {
        if (selectedButton != null) {
            selectedButton.setChecked(false);
        }
        selectedButton = btn;
        selectedButton.setChecked(true);
    }

    private void selectButtonIfExists(java.util.List<ImageTextButton> buttons) {
        if (!buttons.isEmpty()) {
            selectButton(buttons.get(0));
        } else {
            if (selectedButton != null) {
                selectedButton.setChecked(false);
                selectedButton = null;
            }
        }
    }

    public Animal getSelectedProductData() {
        if (selectedButton == null) return null;
        return buttonToProduct.get(selectedButton);
    }


    public boolean productDataNeedsUpdate() {
        return productDataNeedsUpdate;
    }

    public void setProductDataNeedsUpdate(boolean productDataNeedsUpdate) {
        this.productDataNeedsUpdate = productDataNeedsUpdate;
    }
}