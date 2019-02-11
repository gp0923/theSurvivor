package SrvMod.cards;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.MetricsCard;
import SrvMod.patches.Enum;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Scavenge extends MetricsCard implements ModalChoice.Callback {
    public static final String ID = "Survivor:Scavenge";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int CARDS_TO_CHOOSE = 3;

    private static AbstractCard[] improvCards;

    public Scavenge() {
        super(ID, NAME, "Survivor/images/cards/skill_placeholder.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                Enum.SURVIVOR_GREEN, CardRarity.COMMON,
                CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        improvCards = SurvivorMod.getRandomImprovisedCards(CARDS_TO_CHOOSE);
        ModalChoiceBuilder builder =  new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.COLORLESS);
        for (AbstractCard card : improvCards) {
            builder.addOption(card.name, card.rawDescription, CardTarget.NONE);
        }

        ModalChoice modal = builder.create();
        modal.open();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {
        AbstractCard c = improvCards[i].makeCopy();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
    }
}
