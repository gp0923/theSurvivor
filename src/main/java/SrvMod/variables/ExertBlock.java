package SrvMod.variables;

import SrvMod.SurvivorMod;
import SrvMod.cards.helperCards.AbstractExertCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExertBlock extends DynamicVariable {


    @Override
    public String key() {
        return "XB";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {

        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.isExertBlockModified;
        }
        SurvivorMod.log("Warning: Attempted to get exert block modified of non-exert Card");
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.block + card.exertBlockMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert block of non-exert Card");
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.baseBlock + card.baseExertBlockMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert base block of non-exert Card");
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if (abstractCard instanceof AbstractExertCard){
            AbstractExertCard card = (AbstractExertCard)abstractCard;
            return card.upgradedExertBlockMod;
        }
        SurvivorMod.log("Warning: Attempted to get exert block upgraded of non-exert Card");
        return false;
    }
}
