package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;
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

    private Image bodyImage, handImage, pantImage, hairImage, shirtImage;

    {
        // player image:
        int pantIndex = App.getGame().getCurrentPlayer().getPantIndex();
        int shirtIndex = App.getGame().getCurrentPlayer().getShirtIndex();
        int hairIndex = App.getGame().getCurrentPlayer().getHairIndex();

        Texture bodySheetTexture = new Texture("player/body_boy.png");
        TextureRegion bodyRegion = new TextureRegion(bodySheetTexture, 0, 0, 16, 32);
        Texture handSheetTexture = new Texture("player/hand_01.png");
        TextureRegion handRegion = new TextureRegion(handSheetTexture, 0, 0, 16, 32);

        bodyImage = new Image(bodyRegion);
        handImage = new Image(handRegion);

        TextureRegion[][] shirtSheet = TextureRegion.split(new Texture("player/clothes/shirts.png"), 8, 8);
        TextureRegion[][] hairSheet = TextureRegion.split(new Texture("player/clothes/hairstyles.png"), 16, 32);
        TextureRegion[][] pantSheet = TextureRegion.split(new Texture("player/pants/pant_" + pantIndex + ".png"), 16, 32);


        pantImage = new Image(pantSheet[0][0]);
        shirtImage = new Image(shirtSheet[(shirtIndex / 18) * 4][shirtIndex % 16]);
        hairImage = new Image(hairSheet[(hairIndex / 8) * 3][hairIndex % 8]);
    }

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

        Image characterBackground = new Image(new Texture("etc/menu/daybg.png"));
        characterBackground.setScaling(Scaling.fit);
        Group characterGroup = getCharacterGroup();

        Stack characterStack = new Stack();
        characterStack.add(characterBackground);
        characterStack.add(characterGroup);

        leftPart.add(characterStack).size(
                characterBackground.getWidth() * 1.7f,
                characterBackground.getHeight() * 1.7f
        ).padBottom(30);

        leftPart.row();

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
            TextTooltip tooltip = new TextTooltip(getSkillDescription(skill), skin, "Letter");
            skillLabel.addListener(tooltip);
            row.add(skillLabel).padRight(15);
            titleTable.add(row).right().padBottom(50).row();
        }

        for (Skill skill : Arrays.asList(Skill.Farming, Skill.Mining, Skill.Fishing, Skill.Foraging)) {
            Table row = new Table();
            row.left();
            Image skillIcon = new Image(ItemTextureBank.getTexture("skill " + skill.name() + " icon"));
            //Image skillIcon = new Image(new Texture(Gdx.files.internal("inventory/iconSkill" + skill.name() + ".png")));
            // Tooltip
            TextTooltip tooltip = new TextTooltip(getSkillDescription(skill), skin, "Letter");
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

    private Group getCharacterGroup() {
        Group characterGroup = new Group();
        Player player = App.getGame().getCurrentPlayer();

        float scale = 8f;

        int x = 45;
        int y = 49;

        bodyImage.setSize(16 * scale, 32 * scale);
        bodyImage.setPosition(x, y);
        characterGroup.addActor(bodyImage);

        // selected:
        pantImage.setSize(16 * scale, 32 * scale);
        pantImage.setPosition(x, y);
        pantImage.setColor(App.getColor(player.getPantColor()));
        characterGroup.addActor(pantImage);

        shirtImage.setSize(8 * scale, 8 * scale);
        shirtImage.setPosition(x + 4 * scale, y + 9 * scale);
        characterGroup.addActor(shirtImage);

        int longHair = (player.getHairIndex() < 16) ? 0 : -1;
        hairImage.setSize(16 * scale, 32 * scale);
        hairImage.setPosition(x, y - (1 + longHair) * scale);
        hairImage.setColor(App.getColor(player.getHairColor()));
        characterGroup.addActor(hairImage);

        // hand
        handImage.setSize(16 * scale, 32 * scale);
        handImage.setPosition(x, y);
        characterGroup.addActor(handImage);

        return characterGroup;
    }
}