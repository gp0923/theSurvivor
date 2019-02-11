package SrvMod.cards.improvisedCards;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.AbstractExertCard;
import SrvMod.patches.Enum;
import SrvMod.patches.SurvivorTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImprovisedWeapon extends AbstractExertCard{
    public static final String ID = "Survivor:ImprovisedWeapon";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int EX_ATTACK_MOD = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int UPGRADE_EX_PLUS_MOD = 4;
    private static final int EXERT_COST = 1;



    public ImprovisedWeapon() {
        super(ID, NAME, "Survivor/images/cards/attack_placeholder.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                CardColor.COLORLESS, CardRarity.SPECIAL,
                AbstractCard.CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;
        this.baseExertDamageMod = EX_ATTACK_MOD;
        this.exertDamageMod = this.baseExertDamageMod;
        this.tags.add(SurvivorTags.IMPROVISED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canExert(p, this , EXERT_COST, 0)){
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.DamageAction(
                            m,
                            new DamageInfo(p, this.damage + this.exertDamageMod, this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HEAVY));
            p.loseEnergy(EXERT_COST);
            this.exhaustOnUseOnce = true;
            SurvivorMod.log("Exerted Improvised Weapon");
        }else{
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.DamageAction(
                    m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeExertDamageMod(UPGRADE_EX_PLUS_MOD);
        }
    }

}
