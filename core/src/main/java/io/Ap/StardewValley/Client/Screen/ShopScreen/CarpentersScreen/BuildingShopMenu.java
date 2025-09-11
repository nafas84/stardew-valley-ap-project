package io.Ap.StardewValley.Client.Screen.ShopScreen.CarpentersScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.Shop.CarpentersShop.CarpentersShop;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ProductInformation;
import io.Ap.StardewValley.Client.Screen.ShopScreen.SellerDescription;
import io.Ap.StardewValley.Client.Screen.ShopScreen.SellerPicture;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ShopStock;

public class BuildingShopMenu extends Table {
    private SellerPicture sellerPicture;
    private SellerDescription sellerDescription;
    private ShopStock shopStock;
    private ProductInformation productInformation;
    private CarpentersShop shop;
    private ImageButton closeButton;


    public BuildingShopMenu(Skin skin, CarpentersShop shop, String texturePath, Stage stage) {
        super(skin);
        this.shop = shop;

        closeButton = new ImageButton(skin, "trash");
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreenController.setVisibleShop(null);
            }
        });

        Table up = new Table();
        Table left = new Table();
        Table middle = new Table();
        Table right = new Table();

        up.add(closeButton).right().size(100, 100);

        Texture sellerTexture = new Texture(texturePath);
        sellerPicture = new SellerPicture(skin, sellerTexture, shop.getName());
        sellerDescription = new SellerDescription(skin, shop.getShopInformation());

        left.add(sellerPicture).size(330, 320).row();
        left.add(sellerDescription).size(330, 380).row();


        shopStock = new ShopStock(skin, shop.getBuildingData(), shop.getType());
        middle.add(shopStock).size(600, 700);

        productInformation = new ProductInformation(skin, new ProductData("", 0, 0, ""), shop, stage);
        right.add(productInformation).size(350, 700);

        this.add(up).right().row();

        Table windowsTable = new Table();
        windowsTable.add(left);
        windowsTable.add(middle);
        windowsTable.add(right);

        this.add(windowsTable);

    }

    public void updateShop() {
        if (productInformation.shopStockNeedsUpdate()) {
            shopStock.update(shop.getBuildingData());
            productInformation.setShopStockNeedsUpdate(false);
        }
        if (shopStock.productDataNeedsUpdate()) {
            productInformation.update(shopStock.getSelectedProductData());
            shopStock.setProductDataNeedsUpdate(false);
        }
    }
}