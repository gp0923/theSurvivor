package SrvMod.characters;

import SrvMod.SurvivorMod;
import SrvMod.cards.Defend_Survivor;
import SrvMod.patches.Enum;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class TheSurvivor extends CustomPlayer {

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 80;
    public static final int MAX_HP = 80;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public static final String SURVIVOR_SHOULDER_2 = "Survivor/images/char/shoulder2.png"; // campfire pose
    public static final String SURVIVOR_SHOULDER_1 = "Survivor/images/char/shoulder.png"; // another campfire pose
    public static final String SURVIVOR_CORPSE = "Survivor/images/char/corpse.png"; // dead corpse

    //TODO Create a custom skeleton
    public static final String SURVIVOR_SKELETON_ATLAS = "Survivor/images/char/idle/Survivor.atlas"; // spine animation atlas
    public static final String SURVIVOR_SKELETON_JSON = "Survivor/images/char/idle/Survivor.json"; // spine animation json
    private static final String SURVIVOR_ANIMATION = "Idle";// Sprite / Idle
    private static final String[] ORB_TEXTURES = {
            "Survivor/images/ui/epanel/layer6.png",
            "Survivor/images/ui/epanel/layer5.png",
            "Survivor/images/ui/epanel/layer4.png",
            "Survivor/images/ui/epanel/layer3.png",
            "Survivor/images/ui/epanel/layer2.png",
            "Survivor/images/ui/epanel/layer1.png",
            "Survivor/images/ui/epanel/layer5d.png",
            "Survivor/images/ui/epanel/layer4d.png",
            "Survivor/images/ui/epanel/layer3d.png",
            "Survivor/images/ui/epanel/layer2d.png",
            "Survivor/images/ui/epanel/layer1d.png"
    }; //TODO create custom orb

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Survivor");
    public static final String[] TEXT = uiStrings.TEXT; //The title and flavor strings for the TheSurvivor

    private static final String ORB_VFX = "Survivor/images/char/orb/energy_green_VFX.png";
    private static final float[] LAYER_SPEED =
            {-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    public TheSurvivor(String name){
        super(name, Enum.SURVIVOR_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        SurvivorMod.log("Creating the character...");

        initializeClass(null,
                SURVIVOR_SHOULDER_2,
                SURVIVOR_SHOULDER_1,
                SURVIVOR_CORPSE,
                getLoadout(),
                20.0F, -10.0F, 220.0F, 290.0F,
                new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(SURVIVOR_SKELETON_ATLAS, SURVIVOR_SKELETON_JSON, 2.0F);
        // if you're using modified versions of base game animations or made animations in spine make sure to include this bit and the following lines
        AnimationState.TrackEntry e = this.state.setAnimation(0, SURVIVOR_ANIMATION, true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(1.0F);
        SurvivorMod.log("init finished");
    }



    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Survivor:Strike");
        retVal.add("Survivor:Strike");
        retVal.add("Survivor:Strike");
        retVal.add("Survivor:Strike");
        retVal.add("Survivor:Defend");
        retVal.add("Survivor:Defend");
        retVal.add("Survivor:Defend");
        retVal.add("Survivor:Defend");
        retVal.add("Survivor:Strike"); //TODO: Change to appropriate cards
        retVal.add("Survivor:Defend");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Bag of Preparation"); //TODO: Create starting relic.
        UnlockTracker.markRelicAsSeen("Bag of Preparation");
        return retVal;
    }

    // the rest of the character loadout so includes your character select screen info plus hp and starting gold
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                TEXT[1],
                TEXT[2],
                STARTING_HP,
                MAX_HP,
                ORB_SLOTS,
                STARTING_GOLD,
                HAND_SIZE,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return this.getLocalizedCharacterName();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enum.SURVIVOR_GREEN;
    }

    @Override
    public Color getCardRenderColor() {
        return SurvivorMod.SURVIVOR_GREEN;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Defend_Survivor(); //TODO Change to a better basic card
    }

    @Override
    public Color getCardTrailColor() {
        return SurvivorMod.SURVIVOR_GREEN;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("Shovel", MathUtils.random(-0.2f, 0.2f)); //TODO Custom sound?
        CardCrawlGame.screenShake.shake(
                ScreenShake.ShakeIntensity.LOW,
                ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "Shovel"; //TODO Custom sound?
    }

    @Override
    public String getLocalizedCharacterName() {
        return TEXT[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheSurvivor(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[0];
    }

    @Override
    public Color getSlashAttackColor() {
        return SurvivorMod.SURVIVOR_GREEN;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY };
        //TODO Customize heart slash effect
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0]; //TODO Custom vampire text
    }

    //TODO Override initializeStarterDeck() for other mod compatability
}
