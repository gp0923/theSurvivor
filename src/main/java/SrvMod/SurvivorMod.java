package SrvMod;

import SrvMod.cards.Defend_Survivor;
import SrvMod.cards.Scavenge;
import SrvMod.cards.Strike_Survivor;
import SrvMod.cards.improvisedCards.ImprovisedPoison;
import SrvMod.cards.improvisedCards.ImprovisedShield;
import SrvMod.cards.improvisedCards.ImprovisedWeapon;
import SrvMod.characters.TheSurvivor;
import SrvMod.patches.SurvivorTags;
import SrvMod.variables.ExertBlock;
import SrvMod.variables.ExertDamage;
import SrvMod.variables.ExertMagicNumber;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;

import SrvMod.patches.Enum;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


@SpireInitializer
public class SurvivorMod implements PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, PreStartGameSubscriber,
        EditStringsSubscriber, SetUnlocksSubscriber, EditRelicsSubscriber {

    public static final Logger logger = LogManager.getLogger(SurvivorMod.class.getName());

    public static UIStrings uiStrings;
    public static String[] TEXT;

    public static final Color SURVIVOR_GREEN = CardHelper.getColor(34f, 139f, 34f);

    public static ArrayList<AbstractCard> improvisedCardList;

    public SurvivorMod(){


        BaseMod.subscribe(this);

    }

    public static void log(String s) {
        logger.info(s);
    }

    public static void initialize() {
        log("================Initializing the Survivor================");
        BaseMod.addColor(
                Enum.SURVIVOR_GREEN,
                SURVIVOR_GREEN,
                "Survivor/images/cardui/512/bg_attack_green.png",
                "Survivor/images/cardui/512/bg_skill_green.png",
                "Survivor/images/cardui/512/bg_power_green.png",
                "Survivor/images/cardui/512/card_green_orb.png",
                "Survivor/images/cardui/1024/bg_attack_green.png",
                "Survivor/images/cardui/1024/bg_skill_green.png",
                "Survivor/images/cardui/1024/bg_power_green.png",
                "Survivor/images/cardui/1024/card_green_orb.png",
                "Survivor/images/cardui/description_green_orb.png");
                //TODO make custom card frames

        improvisedCardList = new ArrayList<>();

        log("Survivor mod constructor");
        new SurvivorMod();  //Subscribes the mod to basemod
        log("================Finished Initializing the Survivor================");
    }

    @Override
    public void receivePostInitialize() {

        //Add potions here

        uiStrings = CardCrawlGame.languagePack.getUIString("ModInfo");
        TEXT = uiStrings.TEXT;

        //TODO Add badge texture here

        this.loadAudio();
    }

    private void loadAudio(){
        //TODO load additional audio clips here
    }

    @Override
    public void receiveEditCards() {
        //Add Dynamic Variables
        BaseMod.addDynamicVariable(new ExertBlock());
        BaseMod.addDynamicVariable(new ExertDamage());
        BaseMod.addDynamicVariable(new ExertMagicNumber());

        //Add Cards
        addTaggedCard(new Defend_Survivor());
        addTaggedCard(new Strike_Survivor());
        addTaggedCard(new ImprovisedWeapon());
        addTaggedCard(new ImprovisedShield());
        addTaggedCard(new ImprovisedPoison());
        addTaggedCard(new Scavenge());
    }

    /**
     * A wrapper for the addCard method that also populates lists based on tags (for getting random cards).
     * Call this instead of BaseMod.AddCard(c)
     * @param c The card to add
     */
    private void addTaggedCard(AbstractCard c){
        BaseMod.addCard(c);

        //Improvised Cards
        if (c.hasTag(SurvivorTags.IMPROVISED)){
            improvisedCardList.add(c);
        }
    }

    /**
     * Returns an array of cards with the Improvise tag. Cards will be unique.
     * If there are not enough cards to randomly choose the requested amount,
     * the return array will contain one of each improvise card.
     * @param amount The number of cards to generate.
     * @return An array of improvise cards. Copy the cards when using them.
     */
    public static AbstractCard[] getRandomImprovisedCards(int amount){
        if (improvisedCardList.size() <= amount)
            return improvisedCardList.toArray(new AbstractCard[improvisedCardList.size()]);
        AbstractCard[] temp = new AbstractCard[amount];
        int[] rands = new Random().ints(0, improvisedCardList.size()).distinct().limit(amount).toArray();
        for (int i = 0; i < amount; i++){
            temp[i] = improvisedCardList.get(rands[i]);
        }
        return temp;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new TheSurvivor(CardCrawlGame.playerName),
                // Enum.CHRONO_GOLD,
                "Survivor/images/char/survivorButton.png",
                "Survivor/images/char/survivorPortrait.jpg",
                Enum.SURVIVOR_CLASS); //TODO: create custom button and portrait

    }

    @Override
    public void receiveEditKeywords() {
        String lang;
        switch (Settings.language) {
            // case KOR:
            //     language = "kor";
            //     break;
            // case ZHS:
            //     language = "zhs";
            //     break;
            // case ZHT:
            //     language = "zht";
            //     break;
            // case FRA:
            //     language = "fra";
            //     break;
            // case JPN:
            //     language = "jpn";
            //     break;
            default:
                lang = "eng";
        }
        Gson gson = new Gson();
        Map<String, Keyword> keywords;

        String keywordStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorKeywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Type typeToken = new TypeToken<Map<String, Keyword>>() {}.getType();

        keywords = gson.fromJson(keywordStrings, typeToken);

        keywords.forEach((k,v)->{
            log("Adding Keyword - " + v.NAMES[0]);
            BaseMod.addKeyword(v.NAMES, v.DESCRIPTION);
        });
    }

    @Override
    public void receiveEditRelics() {
        //TODO load custom relics
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        switch (Settings.language) {
            // case KOR:
            //     language = "kor";
            //     break;
            // case ZHS:
            //     language = "zhs";
            //     break;
            // case ZHT:
            //     language = "zht";
            //     break;
            // case FRA:
            //     language = "fra";
            //     break;
            // case JPN:
            //     language = "jpn";
            //     break;
            default:
                lang = "eng";
        }

        // RelicStrings
        String relicStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorRelics.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        // CardStrings
        String cardStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorCards.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        // OrbStrings
        String orbStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorOrbs.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);

        // PowerStrings
        String powerStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorPowers.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        // PowerStrings
        String potionStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorPotions.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        // Eventstring
        String eventStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorEvents.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);

        // UIstring
        String uiStrings = Gdx.files.internal("Survivor/localization/" + lang + "/survivorUI.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO
    }

    @Override
    public void receivePreStartGame() {
        //TODO add things that need to be reset at the beginning of each game
    }
}
