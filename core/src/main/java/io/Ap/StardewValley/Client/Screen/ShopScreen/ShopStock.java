package io.Ap.StardewValley.Client.Screen.ShopScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopStock extends Window {

    private final ShopType shopType;
    private final CheckBox onlyAvailableCheckBox;
    private final ScrollPane allItemsScroll;
    private final ScrollPane availableItemsScroll;

    private final Table allItemsTable;
    private final Table availableItemsTable;

    private final List<ImageTextButton> allButtons = new ArrayList<>();
    private final List<ImageTextButton> availableButtons = new ArrayList<>();

    private final Skin skin;

    private final Map<ImageTextButton, ProductData> buttonToProduct = new HashMap<>();
    private ImageTextButton selectedButton = null;

    private boolean productDataNeedsUpdate = false;

    public ShopStock(Skin skin, List<ProductData> products, ShopType shopType) {
        super("Shop Stock", skin);
        this.skin = skin;
        this.shopType = shopType;

        setSize(500, 700);
        setMovable(false);
        setResizable(false);
        setModal(false);
        setKeepWithinStage(true);

        onlyAvailableCheckBox = new CheckBox("", skin);
        allItemsTable = new Table(skin);
        availableItemsTable = new Table(skin);

        allItemsScroll = new ScrollPane(allItemsTable, skin, "inventory");
        availableItemsScroll = new ScrollPane(availableItemsTable, skin, "inventory");

        onlyAvailableCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateVisibleScrollPane();
            }
        });
        add(onlyAvailableCheckBox).left().pad(10).colspan(1);
        row();

        Stack scrollStack = new Stack();
        scrollStack.add(allItemsScroll);
        scrollStack.add(availableItemsScroll);
        availableItemsScroll.setVisible(false);
        add(scrollStack).expand().fill();

        update(products);
    }

    private void updateVisibleScrollPane() {
        if (onlyAvailableCheckBox.isChecked()) {
            allItemsScroll.setVisible(false);
            availableItemsScroll.setVisible(true);
        } else {
            allItemsScroll.setVisible(true);
            availableItemsScroll.setVisible(false);
        }
        // وقتی visible عوض شد، اگر selectedButton داخل آن ScrollPane نیست، باید انتخاب رو تغییر بدیم:
        if (availableItemsScroll.isVisible()) {
            if (selectedButton == null || !availableButtons.contains(selectedButton)) {
                selectButtonIfExists(availableButtons);
            }
        } else {
            if (selectedButton == null || !allButtons.contains(selectedButton)) {
                selectButtonIfExists(allButtons);
            }
        }
    }

    public void update(List<ProductData> products) {
        allItemsTable.clear();
        availableItemsTable.clear();
        allButtons.clear();
        availableButtons.clear();
        buttonToProduct.clear();
        selectedButton = null;

        for (ProductData product : products) {
            ImageTextButton btn = createProductButton(product);
            allButtons.add(btn);
            buttonToProduct.put(btn, product);
            allItemsTable.add(btn).expandX().fillX().row();

            if (product.exists()) {
                ImageTextButton btnAvailable = createProductButton(product);
                availableButtons.add(btnAvailable);
                buttonToProduct.put(btnAvailable, product);
                availableItemsTable.add(btnAvailable).expandX().fillX().row();
            }
        }

        // اضافه کردن لیسنر به همه دکمه‌ها
        addClickListenerToButtons();

        // انتخاب اولین دکمه لیست available به صورت پیش‌فرض
        if (!availableButtons.isEmpty()) {
            selectButton(availableButtons.get(0));
        } else if (!allButtons.isEmpty()) {
            selectButton(allButtons.get(0));
        }

        updateVisibleScrollPane();
        pack();
    }

    private ImageTextButton createProductButton(ProductData product) {
        TextureRegion texture = ItemTextureBank.getTexture(product.getName());
        Image image = new Image(texture);

        Table contentTable = new Table();

        contentTable.add(image).size(50, 50).padRight(10);

        Label nameLabel = new Label(product.getName(), skin);
        nameLabel.setAlignment(Align.left);
        contentTable.add(nameLabel).expandX().left();

        Label priceLabel = new Label(String.valueOf(product.getPrice()), skin);
        priceLabel.setAlignment(Align.right);
        contentTable.add(priceLabel).width(70).right();

        ImageTextButton button = new ImageTextButton("", skin);
        button.clearChildren();
        button.add(contentTable).expand().fill();

        if (!product.exists()) {
            button.setDisabled(true);
            nameLabel.setColor(0f, 0f, 0f, 1f);
            priceLabel.setColor(0.6f, 0.6f, 0.6f, 1f);
        }

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
        for (ImageTextButton btn : availableButtons) {
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

    private void selectButtonIfExists(List<ImageTextButton> buttons) {
        if (!buttons.isEmpty()) {
            selectButton(buttons.get(0));
        } else {
            if (selectedButton != null) {
                selectedButton.setChecked(false);
                selectedButton = null;
            }
        }
    }

    public ProductData getSelectedProductData() {
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