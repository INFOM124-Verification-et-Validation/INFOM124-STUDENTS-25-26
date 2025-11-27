package com.gildedrose;

public class Sulfuras extends Item {


    public Sulfuras(int sellIn) {
        this.sellIn = sellIn;
        this.quality = 80;
    }

    public void updateQuality() {

    }

    @Override
    public String toString() {
        return "Sulfuras, " + this.sellIn + ", " + this.quality;
    }
}
