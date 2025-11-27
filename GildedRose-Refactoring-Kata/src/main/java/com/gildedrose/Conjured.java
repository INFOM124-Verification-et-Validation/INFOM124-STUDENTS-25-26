package com.gildedrose;

import static java.lang.Math.max;

public class Conjured extends Item {

    public Conjured(int sellIn, int quality) {
            this.sellIn = sellIn;
            this.quality = quality;
    }

    public void updateQuality() {
            quality = max(quality - 2, 0);
    }

    @Override
    public String toString() {
        return "Conjured, " + this.sellIn + ", " + this.quality;
    }
}
