package SrvMod.cards.improvisedCards;

import SrvMod.cards.helperCards.MetricsCard;
import SrvMod.patches.SurvivorTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class ImprovisedPoison extends MetricsCard {
    public static final String ID = "Survivor:ImprovisedPoison";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int POISON_AMT = 5;
    private static final int UPGRADE_PLUS_POISON = 2;

    public ImprovisedPoison() {
        super(ID, NAME, "Survivor/images/cards/skill_placeholder.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
            CardColor.COLORLESS, CardRarity.SPECIAL,
                CardTarget.ENEMY);
        this.baseMagicNumber = POISON_AMT;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(SurvivorTags.IMPROVISED);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_POISON);
        }
    }
}
