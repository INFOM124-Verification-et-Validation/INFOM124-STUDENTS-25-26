package com.gildedrose;

import static java.lang.Math.min;

public class AgedBrie extends Item {
    public AgedBrie(int sellIn, int quality) {
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public void updateQuality() {
        quality = min(quality + 1, 50);
    }

    @Override
    public String toString() {
        return "Aged brie, " + this.sellIn + ", " + this.quality;
    }
}
