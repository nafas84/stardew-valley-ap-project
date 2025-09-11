package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;

import java.util.*;
import java.util.List;

public class SkillsTab extends Window {
    private final Skin skin;
    private final List<ImageTextButton> inventoryButtons;
    private ImageTextButton selectedButton;

    private final Table rightPart;
    private final Map<Skill, Image> skillHeartImages = new EnumMap<>(Skill.class);
    private final Map<Skill, Tooltip<Label>> skillTooltips = new EnumMap<>(Skill.class);

    public SkillsTab(Skin skin) {
        super("", skin);
        this.skin = skin;
        this.inventoryButtons = new ArrayList<>();

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        Table leftPart = createLeftPart();
        rightPart = createRightPart();

        this.add(leftPart).width(350).top();
        this.add(rightPart).width(710).top().left().pad(50, 0, 0, 0);
    }

    private Table createLeftPart() {
        Table leftPart = new Table();
        leftPart.top();

        Image topImage = new Image(new Texture("etc/menu/daybg.png"));
        topImage.setScaling(Scaling.fit);
        leftPart.add(topImage).width(200).height(350).center().row();

        Label label1 = new Label(App.getCurrentUser().getNickname() , skin);
        Label label2 = new Label("count: " + App.getGame().getCurrentPlayer().getCount(), skin);
        leftPart.add(label1).center().padTop(10).row();
        leftPart.add(label2).center().padTop(5).row();

        return leftPart;
    }

    private Table createRightPart() {
        Table table = new Table();
        Table titleTable = new Table();
        Table iconTable = new Table();
        Table heartsTable = new Table();
        table.top().left();



        for (Skill skill : Arrays.asList(Skill.Farming, Skill.Mining, Skill.Fishing, Skill.Foraging)) {
            Table row = new Table();
            row.left();
            Label skillLabel = new Label(skill.name(), skin);
            // Tooltip
            TextTooltip tooltip = new TextTooltip(getSkillDescription(skill), skin, "letter");
            skillLabel.addListener(tooltip);
            row.add(skillLabel).padRight(15);
            titleTable.add(row).right().padBottom(50).row();
        }

        for (Skill skill : Arrays.asList(Skill.Farming, Skill.Mining, Skill.Fishing, Skill.Foraging)) {
            Table row = new Table();
            row.left();
            Image skillIcon = new Image(ItemTextureBank.getTexture("skill " + skill.name().toLowerCase() + " icon"));
            // Tooltip
            TextTooltip tooltip = new TextTooltip(getSkillDescription(skill), skin, "letter");
            skillIcon.addListener(tooltip);
            row.add(skillIcon).size(48).padRight(25);
            iconTable.add(row).left().padBottom(45).row();
        }

        for (Skill skill : Arrays.asList(Skill.Farming, Skill.Mining, Skill.Fishing, Skill.Foraging)) {
            Table row = new Table();
            row.left();
            int level = App.getGame().getCurrentPlayer().getAbilityLevel(skill);
            Image hearts = new Image(ItemTextureBank.getTexture(level + " levels"));
            skillHeartImages.put(skill, hearts);
            row.add(hearts).size(350, 60);
            heartsTable.add(row).left().padTop(0).padBottom(33).row();
        }

        table.add(titleTable);
        table.add(iconTable);
        table.add(heartsTable);

//        table.setDebug(true);
        return table;
    }

    private String getSkillDescription(Skill skill) {
        switch (skill) {
            case Farming:
                return "related to plants and animals";
            case Mining:
                return "related to destroying stones, mines, etc";
            case Fishing:
                return "whenever you catch a fish, you've increased this skill";
            case Foraging:
                return "related to foraging!";
            default:
                return "No description available.";
        }
    }

    public void updateInfo() {
        for (Skill skill : skillHeartImages.keySet()) {
            int level = App.getGame().getCurrentPlayer().getAbilityLevel(skill);
            skillHeartImages.get(skill).setDrawable(new TextureRegionDrawable(ItemTextureBank.getTexture(level + " levels")));
        }
    }
}