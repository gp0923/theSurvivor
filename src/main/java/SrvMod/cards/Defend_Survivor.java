package SrvMod.cards;

import SrvMod.patches.Enum;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Survivor extends MetricsCard {
    public static final String ID = "Survivor:Defend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public Defend_Survivor() {
        super(ID, NAME, "Survivor/images/cards/skill_placeholder.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                Enum.SURVIVOR_GREEN, AbstractCard.CardRarity.BASIC,
                AbstractCard.CardTarget.SELF);
        this.baseBlock = BLOCK_AMT;
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_BLOCK);
        }
    }

    @Override
    public boolean isDefend() {
        return true;
    }
}
