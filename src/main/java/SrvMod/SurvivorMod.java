package SrvMod;

import SrvMod.cards.Defend_Survivor;
import SrvMod.cards.Strike_Survivor;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;

import SrvMod.patches.Enum;

public class SurvivorMod implements PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, PreStartGameSubscriber,
        EditStringsSubscriber, SetUnlocksSubscriber, EditRelicsSubscriber {

    public static final Logger logger = LogManager.getLogger(SurvivorMod.class.getName());
    public static final Color SURVIVOR_GREEN = CardHelper.getColor(34f, 139f, 34f);

    public SurvivorMod(){
        BaseMod.subscribe(this);

    }

    public static void log(String s) {
        logger.info(s);
    }

    @Override
    public void receivePostInitialize() {
        //TODO
    }

    @Override
    public void receivePreStartGame() {

    }

    public static void initialize() {
        logger.info("================Initializing theSurvivor================");
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
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Defend_Survivor());
        BaseMod.addCard(new Strike_Survivor());
    }

    @Override
    public void receiveEditCharacters() {
        //TODO
    }

    @Override
    public void receiveEditKeywords() {
        //TODO
    }

    @Override
    public void receiveEditRelics() {
        //TODO
    }

    @Override
    public void receiveEditStrings() {
        //TODO
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO
    }
}
