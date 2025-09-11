package io.Ap.StardewValley.Client.Screen.ShopScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.MapController;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;

public class ProductInformation extends Window {

    private Skin skin;
    private Stage stage;
    private Shop shop;
    private ProductData product;
    private final Label nameLabel;
    private final Label descriptionLabel;

    private final Label quantityLabel;
    private final ImageButton plusButton;
    private final ImageButton minusButton;

    private final TextButton buyButton;

    private int quantity = 1;

    private boolean shopStockNeedsUpdate = false;

    private final Table quantityTable;
    private final Table coordinateTable;
    private final Stack controlStack; // برای سوئیچ بین دو حالت

    private final TextField xField;
    private final TextField yField;

    public ProductInformation(Skin skin, ProductData product, Shop shop, Stage stage) {
        super("", skin);
        this.shop = shop;
        this.product = product;
        this.skin = skin;
        this.stage = stage;

        setSize(350, 700);
        setMovable(false);
        setResizable(false);
        setModal(false);
        setKeepWithinStage(true);

        // Labels for name and description
        nameLabel = new Label(product.getName(), skin);
        descriptionLabel = new Label(product.getDescription(), skin);

        nameLabel.setAlignment(Align.left);
        descriptionLabel.setAlignment(Align.left);
        descriptionLabel.setWrap(true);

        // Quantity controls
        quantityLabel = new Label(String.valueOf(quantity), skin);
        quantityLabel.setAlignment(Align.center);

        plusButton = new ImageButton(skin, "Right");
        minusButton = new ImageButton(skin, "Left");

        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quantity++;
                updateQuantityLabel();
            }
        });

        minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (quantity > 1) {
                    quantity--;
                    updateQuantityLabel();
                }
            }
        });

        // جدول quantity
        quantityTable = new Table(skin);
        quantityTable.add(minusButton).size(50, 50).padRight(10);
        quantityTable.add(quantityLabel).width(50).center();
        quantityTable.add(plusButton).size(50, 50).padLeft(10);

        // جدول coordinate (x و y)
        coordinateTable = new Table(skin);
        xField = new TextField("", skin);
        yField = new TextField("", skin);

        coordinateTable.add(new Label("x:", skin)).padRight(7);
        coordinateTable.add(xField).size(100, 70).padRight(20);
        coordinateTable.add(new Label("y:", skin)).padRight(7);
        coordinateTable.add(yField).size(100, 70);

//        coordinateTable.setDebug(true);

        coordinateTable.setVisible(false); // پیش‌فرض مخفی

        // ساخت Stack
        controlStack = new Stack();
        controlStack.add(quantityTable);
        controlStack.add(coordinateTable);

        // Buy button
        buyButton = new TextButton("Buy", skin);

        // -------------------------------
        Table rootTable = new Table(skin);
        rootTable.setFillParent(false);

        Table infoTable = new Table(skin);
        infoTable.top().left().pad(20);
        infoTable.add(nameLabel).left().expandX().fillX().row();
        infoTable.add(descriptionLabel).left().expandX().fillX().padTop(10).row();

        Table bottomTable = new Table(skin);
        bottomTable.center().pad(20);

        bottomTable.add(controlStack).padBottom(20).expand().row();
        bottomTable.add(buyButton).padTop(10);

        rootTable.add(infoTable).fillX().row();
        rootTable.add(bottomTable).expandY().bottom().fillX();

        add(rootTable).expand().fill();

        addBuyListener();
    }

    private void updateQuantityLabel() {
        quantityLabel.setText(String.valueOf(quantity));
    }

    public void addBuyListener() {
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (quantityTable.isVisible()) {
                    Result result = shop.buy(product.getName(), quantity, null);
                    if (result.isSuccessful()) {
                        if (result.message().equals("use build command for buildings!")) {
                            // سوئیچ به حالت coordinate
                            quantityTable.setVisible(false);
                            coordinateTable.setVisible(true);
                        } else {
                            shopStockNeedsUpdate = true;
                        }
                    } else {
                        showError(result.message());
                    }
                } else if (coordinateTable.isVisible()) {
                    Result result = MapController.buildFarmBuildingThroughScreen(product.getName(), xField.getText(), yField.getText());
                    if (result.isSuccessful()) {
                        quantityTable.setVisible(true);
                        coordinateTable.setVisible(false);
                        shopStockNeedsUpdate = true;
                    }
                    else {
                        showError(result.message());
                    }
                }
            }
        };
        buyButton.addListener(listener);
    }

    public void update(ProductData product) {
        this.product = product;
        nameLabel.setText(product.getName());
        descriptionLabel.setText(product.getDescription());
        quantity = 1;
        updateQuantityLabel();
    }

    public boolean shopStockNeedsUpdate() {
        return shopStockNeedsUpdate;
    }

    public void setShopStockNeedsUpdate(boolean shopStockNeedsUpdate) {
        this.shopStockNeedsUpdate = shopStockNeedsUpdate;
    }

    private void showError(String msg) {
        final Window errorWindow = new Window("", skin, "Letter");
        errorWindow.setMovable(false);
        errorWindow.setKeepWithinStage(true);
        errorWindow.add(new Label(msg, skin));
        errorWindow.setSize(700, 90);
        errorWindow.setPosition(600, 170, Align.center);
        errorWindow.pack();

        stage.addActor(errorWindow);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorWindow.remove();
            }
        }, 5);
    }
}