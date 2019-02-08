package SrvMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class Enum {

    @SpireEnum
    public static AbstractCard.CardColor SURVIVOR_GREEN;

    @SpireEnum
    public static AbstractPlayer.PlayerClass SURVIVOR_CLASS;

    @SpireEnum
    public static AbstractCard.CardTags CONSUMABLE_CARD;

    @SpireEnum
    public static AbstractCard.CardTags DAMAGED_CARD;
}
