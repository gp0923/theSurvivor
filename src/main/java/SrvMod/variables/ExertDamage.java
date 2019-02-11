package SrvMod.variables;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.AbstractExertCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExertDamage extends DynamicVariable {


    @Override
    public String key() {
        return "XD";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {

        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.isExertDamageModified;
        }
        SurvivorMod.log("Warning: Attempted to get exert damage modified of non-exert Card");
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.damage + card.exertDamageMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert damage of non-exert Card");
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.baseDamage + card.baseExertDamageMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert base damage of non-exert Card");
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.upgradedExertDamageMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert damage upgraded of non-exert Card");
        return false;
    }
}
