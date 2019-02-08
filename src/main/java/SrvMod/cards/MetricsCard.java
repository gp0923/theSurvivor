package SrvMod.cards;

import basemod.abstracts.CustomCard;

/*
    Base class for cards with recording metrics
 */
public abstract class MetricsCard extends CustomCard {
    public MetricsCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        if (this.name == null) {
            this.name = "";
        }
        if (this.rawDescription == null) {
            this.rawDescription = "";
        }
    }
}
