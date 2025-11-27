package com.gildedrose;

import static java.lang.Math.min;

public class BackstagePasses extends Item {

    public BackstagePasses(int sellIn, int quality) {
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public void updateQuality() {
        if (sellIn < 0) {
            quality = 0;
        } else if (sellIn <= 5) {
            quality = min(quality + 3, 50);
        } else if (sellIn <= 10) {
            quality = min(quality + 2, 50);
        }else {
            quality = quality - 1;
        }
    }

    @Override
    public String toString() {
        return "Backstage passes, " + this.sellIn + ", " + this.quality;
    }
}
