package SrvMod.cards.improvisedCards;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.AbstractExertCard;
import SrvMod.patches.SurvivorTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImprovisedShield extends AbstractExertCard {

    public static final String ID = "Survivor:ImprovisedShield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int BLOCK_AMT = 7;
    private static final int EX_BLOCK_MOD = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int UPGRADE_EX_PLUS_MOD = 4;
    private static final int EXERT_COST = 1;

    public ImprovisedShield() {
        super(ID, NAME, "Survivor/images/cards/skill_placeholder.png", COST, DESCRIPTION, CardType.SKILL,
                CardColor.COLORLESS, CardRarity.SPECIAL,
                CardTarget.SELF);
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.baseExertBlockMod = EX_BLOCK_MOD;
        this.exertBlockMod = this.baseExertBlockMod;
        this.tags.add(SurvivorTags.IMPROVISED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canExert(p, this , EXERT_COST, 0)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block + this.exertBlockMod));
            p.loseEnergy(EXERT_COST);
            this.exhaustOnUseOnce = true;
            SurvivorMod.log("Exerted Improvised Shield");
        }else{
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeExertBlockMod(UPGRADE_EX_PLUS_MOD);
        }
    }
}
