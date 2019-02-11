package SrvMod.cards.helperCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public abstract class AbstractExertCard extends MetricsCard {
    public int exertDamageMod;
    public int exertBlockMod;
    public int exertMagicNumberMod;
    public int baseExertDamageMod;
    public int baseExertBlockMod;
    public int baseExertMagicNumberMod;
    public boolean isExertDamageModified;
    public boolean isExertBlockModified;
    public boolean isExertMagicNumberModified;
    public boolean upgradedExertDamageMod;
    public boolean upgradedExertBlockMod;
    public boolean upgradedExertMagicNumberMod;

    public AbstractExertCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.exertDamageMod = this.exertBlockMod = this.exertMagicNumberMod = 0;
        this.baseExertDamageMod = this.baseExertBlockMod = this.baseExertMagicNumberMod = 0;
        this.upgradedExertDamageMod = this.upgradedExertBlockMod = this.upgradedExertMagicNumberMod = false;
    }

    protected void upgradeExertDamageMod(int amount) {
        this.exertDamageMod += amount;
        this.upgradedExertDamageMod = true;
    }

    protected void upgradeExertBlockMod(int amount) {
        this.baseExertBlockMod += amount;
        this.upgradedExertBlockMod = true;
    }

    protected void upgradeExertMagicNumber(int amount) {
        this.baseExertMagicNumberMod += amount;
        this.exertMagicNumberMod = this.baseExertMagicNumberMod;
        this.upgradedExertMagicNumberMod = true;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();

        this.exertDamageMod = this.baseExertDamageMod;
        this.isExertDamageModified = false;
        this.exertBlockMod = this.baseExertBlockMod;
        this.isExertBlockModified = false;
        this.exertMagicNumberMod = this.baseExertMagicNumberMod;
        this.isExertMagicNumberModified = false;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();

        if (this.upgradedExertDamageMod) {
            this.exertDamageMod = this.baseExertDamageMod;
            this.isExertDamageModified = true;
        }

        if (this.upgradedExertBlockMod) {
            this.exertBlockMod = this.baseExertBlockMod;
            this.isExertBlockModified = true;
        }

        if (this.upgradedExertMagicNumberMod) {
            this.exertMagicNumberMod = this.baseExertMagicNumberMod;
            this.isExertMagicNumberModified = true;
        }

    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (this.isDamageModified)
            this.isExertDamageModified = true;

        if (this.isBlockModified)
            this.isExertBlockModified = true;

        if (this.isMagicNumberModified)
            this.isExertMagicNumberModified = true;
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo){
        super.calculateCardDamage(mo);
        if (this.isDamageModified)
            this.isExertDamageModified = true;
    }

    /**
     * Check if a card can be exerted in the current gamestate.
     * @param p The player.
     * @param c The exert card being played.
     * @param energyCost The additional energy cost to exert the card.
     * @param cardCost The amount of cards that need to be discarded to exert this card.
     * @return True if the card can be exerted, false otherwise.
     */
    public static boolean canExert(AbstractPlayer p, AbstractExertCard c, int energyCost, int cardCost){
        if (EnergyPanel.getCurrentEnergy() >= c.cost + energyCost && p.hand.size() >= cardCost)
            return true;
        return false;
    }
}
