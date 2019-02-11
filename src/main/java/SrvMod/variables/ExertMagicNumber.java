package SrvMod.variables;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.AbstractExertCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExertMagicNumber extends DynamicVariable {


    @Override
    public String key() {
        return "XM";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {

        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.isExertMagicNumberModified;
        }
        SurvivorMod.log("Warning: Attempted to get exert magicNumber modified of non-exert Card");
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.magicNumber + card.exertMagicNumberMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert magicNumber of non-exert Card");
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.baseMagicNumber + card.baseExertMagicNumberMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert base magicNumber of non-exert Card");
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.upgradedExertMagicNumberMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert magicNumber upgraded of non-exert Card");
        return false;
    }
}
