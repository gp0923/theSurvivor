package SrvMod;

import SrvMod.cards.Defend_Survivor;
import SrvMod.cards.Strike_Survivor;
import SrvMod.characters.TheSurvivor;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.reflect.TypeToken;
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
import java.util.Map;

@SpireInitializer
public class SurvivorMod implements PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, PreStartGameSubscriber,
        EditStringsSubscriber, SetUnlocksSubscriber, EditRelicsSubscriber {

    public static final Logger logger = LogManager.getLogger(SurvivorMod.class.getName());

    public static UIStrings uiStrings;
    public static String[] TEXT;

    public static final Color SURVIVOR_GREEN = CardHelper.getColor(34f, 139f, 34f);

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

        log("Survivor mod constructor");

        new SurvivorMod();  //This is necessary but I don't get why we just ditch the reference
        log("================Finished Initializing the Survivor================");
    }

    @Override
    public void receivePreStartGame() {
        //TODO add things that need to be reset at the beginning of each game
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
        BaseMod.addCard(new Defend_Survivor());
        BaseMod.addCard(new Strike_Survivor());
        //TODO add other cards
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
}
