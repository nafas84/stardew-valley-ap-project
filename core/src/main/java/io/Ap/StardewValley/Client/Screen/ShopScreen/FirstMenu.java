package io.Ap.StardewValley.Client.Screen.ShopScreen;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class FirstMenu extends Window {

    public FirstMenu(Skin skin, String name, TextButton firstButton, TextButton secondButton) {
        super(name, skin);

        this.setSize(1050, 270);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        Table table = new Table();
        table.top().left();

        table.add(firstButton).center().size(950, 80).center().pad(10).row();
        table.add(secondButton).center().size(950, 80).center().pad(10).row();

        this.add(table).expand().fill();

//        this.setDebug(true);
    }

    public FirstMenu(Skin skin, String name, TextButton firstButton, TextButton secondButton, TextButton thirdButton) {
        super(name, skin);

        this.setSize(1050, 360);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        Table table = new Table();
        table.top().left();

        table.add(firstButton).size(950, 85).center().pad(5).row();
        table.add(secondButton).size(950, 85).expand().center().pad(5).row();
        table.add(thirdButton).size(950, 85).expand().center().pad(5).row();

        this.add(table).expand().fill();

//        this.setDebug(true);
    }

    public FirstMenu(Skin skin, String name, TextButton firstButton, TextButton secondButton, TextButton thirdButton, TextButton fourthMenu) {
        super(name, skin);

        this.setSize(1050, 480);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        Table table = new Table();
        table.top().left();

        table.add(firstButton).center().size(1000, 100).center().pad(10).row();
        table.add(secondButton).center().size(1000, 100).center().pad(10).row();
        table.add(thirdButton).center().size(1000, 100).center().pad(10).row();
        table.add(fourthMenu).center().size(1000, 100).center().pad(10).row();

        this.add(table).expand().fill();

        //        this.setDebug(true);
    }
}
